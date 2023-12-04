package edu.uci.ics.amber.engine.architecture.pythonworker

import akka.actor.Props
import com.twitter.util.Promise
import com.typesafe.config.{Config, ConfigFactory}
import edu.uci.ics.amber.engine.architecture.common.WorkflowActor
import edu.uci.ics.amber.engine.architecture.common.WorkflowActor.NetworkAck
import edu.uci.ics.amber.engine.architecture.messaginglayer.{
  NetworkInputGateway,
  NetworkOutputGateway
}
import edu.uci.ics.amber.engine.architecture.pythonworker.WorkerBatchInternalQueue.DataElement
import edu.uci.ics.amber.engine.architecture.worker.WorkflowWorker.TriggerSend
import edu.uci.ics.amber.engine.common.actormessage.{Backpressure, CreditUpdate}
import edu.uci.ics.amber.engine.common.ambermessage.WorkflowMessage.getInMemSize
import edu.uci.ics.amber.engine.common.ambermessage._
import edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity
import edu.uci.ics.texera.Utils

import java.nio.file.Path
import java.util.concurrent.{ExecutorService, Executors}
import scala.sys.process.{BasicIO, Process}

object PythonWorkflowWorker {
  def props(
      id: ActorVirtualIdentity
  ): Props =
    Props(
      new PythonWorkflowWorker(
        id
      )
    )
}

class PythonWorkflowWorker(
    actorId: ActorVirtualIdentity
) extends WorkflowActor(actorId) {

  // For receiving the Python server port number that will be available later
  private lazy val portNumberPromise = Promise[Int]()
  // Proxy Server and Client
  private lazy val serverThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor
  private lazy val clientThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor
  private var pythonProxyServer: PythonProxyServer = _
  private lazy val pythonProxyClient: PythonProxyClient =
    new PythonProxyClient(portNumberPromise, actorId)

  val pythonSrcDirectory: Path = Utils.amberHomePath
    .resolve("src")
    .resolve("main")
    .resolve("python")
  val config: Config = ConfigFactory.load("python_udf")
  val pythonENVPath: String = config.getString("python.path").trim
  // Python process
  private var pythonServerProcess: Process = _

  private val networkInputGateway = new NetworkInputGateway(actorId)
  private val networkOutputGateway = new NetworkOutputGateway(
    actorId,
    x => {
      self ! TriggerSend(x)
    }
  )

  def handleSendFromDP: Receive = {
    case TriggerSend(msg) =>
      transferService.send(msg)
  }

  override def receive: Receive = super.receive orElse handleSendFromDP

  override def handleInputMessage(messageId: Long, workflowMsg: WorkflowFIFOMessage): Unit = {
    val channel = networkInputGateway.getChannel(workflowMsg.channel)
    channel.acceptMessage(workflowMsg)
    while (channel.isEnabled && channel.hasMessage) {
      val msg = channel.take
      msg.payload match {
        case payload: ControlPayload =>
          pythonProxyClient.enqueueCommand(payload, workflowMsg.channel)
        case payload: DataPayload =>
          pythonProxyClient.enqueueData(DataElement(payload, workflowMsg.channel))
        case p => logger.error(s"unhandled control payload: $p")
      }
    }
    sender ! NetworkAck(messageId, getInMemSize(workflowMsg), getQueuedCredit(workflowMsg.channel))
  }

  override def receiveCreditMessages: Receive = {
    case WorkflowActor.CreditRequest(channel) =>
      pythonProxyClient.enqueueActorCommand(CreditUpdate())
      sender ! WorkflowActor.CreditResponse(channel, getQueuedCredit(channel))
    case WorkflowActor.CreditResponse(channel, credit) =>
      transferService.updateChannelCreditFromReceiver(channel, credit)
  }

  /** flow-control */
  override def getQueuedCredit(channelID: ChannelID): Long = {
    pythonProxyClient.getQueuedCredit(channelID) + pythonProxyClient.getQueuedCredit
  }

  override def handleBackpressure(enableBackpressure: Boolean): Unit = {
    pythonProxyClient.enqueueActorCommand(Backpressure(enableBackpressure))
  }

  override def postStop(): Unit = {
    super.postStop()
    try {
      // try to send shutdown command so that it can gracefully shutdown
      pythonProxyClient.close()

      clientThreadExecutor.shutdown()

      serverThreadExecutor.shutdown()

      // destroy python process
      pythonServerProcess.destroy()
    } catch {
      case e: Exception =>
        logger.error(s"$e - happened during shutdown")
    }
  }

  override def initState(): Unit = {
    startProxyServer()
    startPythonProcess()
    startProxyClient()
  }

  private def startProxyServer(): Unit = {
    // Try to start the server until it succeeds
    var serverStart = false
    while (!serverStart) {
      pythonProxyServer = new PythonProxyServer(networkOutputGateway, actorId, portNumberPromise)
      val future = serverThreadExecutor.submit(pythonProxyServer)
      try {
        future.get()
        serverStart = true
      } catch {
        case e: Exception =>
          future.cancel(true)
          logger.info("Failed to start the server: " + e.getMessage + ", will try again")
      }
    }
  }

  private def startProxyClient(): Unit = {
    clientThreadExecutor.submit(pythonProxyClient)
  }

  private def startPythonProcess(): Unit = {
    val udfEntryScriptPath: String =
      pythonSrcDirectory.resolve("texera_run_python_worker.py").toString
    pythonServerProcess = Process(
      Seq(
        if (pythonENVPath.isEmpty) "python3"
        else pythonENVPath, // add fall back in case of empty
        "-u",
        udfEntryScriptPath,
        actorId.name,
        Integer.toString(pythonProxyServer.getPortNumber.get()),
        config.getString("python.log.streamHandler.level")
      )
    ).run(BasicIO.standard(false))
  }
}
