package edu.uci.ics.amber.engine.architecture.worker

import edu.uci.ics.amber.engine.architecture.controller.promisehandlers.FatalErrorHandler.FatalError
import edu.uci.ics.amber.engine.architecture.worker.WorkflowWorker.DPInputQueueElement
import edu.uci.ics.amber.engine.architecture.worker.statistics.WorkerState.{READY, UNINITIALIZED}
import edu.uci.ics.amber.engine.common.AmberLogging
import edu.uci.ics.amber.engine.common.actormessage.{ActorCommand, Backpressure}
import edu.uci.ics.amber.engine.common.amberexception.WorkflowRuntimeException
import edu.uci.ics.amber.engine.common.ambermessage.{ChannelID, ControlPayload, DataPayload}
import edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity
import edu.uci.ics.amber.engine.common.virtualidentity.util.{CONTROLLER, SELF}
import edu.uci.ics.amber.error.ErrorUtils.safely

import java.util.concurrent.{
  CompletableFuture,
  ExecutorService,
  Executors,
  Future,
  LinkedBlockingQueue
}

class DPThread(
    val actorId: ActorVirtualIdentity,
    dp: DataProcessor,
    internalQueue: LinkedBlockingQueue[DPInputQueueElement]
) extends AmberLogging {

  // initialize dp thread upon construction
  @transient
  var dpThreadExecutor: ExecutorService = _
  @transient
  var dpThread: Future[_] = _

  var backpressureStatus = false

  def getThreadName: String = "DP-thread"

  private val endFuture = new CompletableFuture[Unit]()

  def stop(): Unit = {
    if (dpThread != null) {
      dpThread.cancel(true) // interrupt
      stopped = true
      endFuture.get()
    }
    if (dpThreadExecutor != null) {
      dpThreadExecutor.shutdownNow() // destroy thread
    }
  }

  @volatile
  private var stopped = false

  def start(): Unit = {
    if (dpThreadExecutor != null) {
      logger.info("DP Thread is already running")
      return
    }
    dpThreadExecutor = Executors.newSingleThreadExecutor
    if (dp.stateManager.getCurrentState == UNINITIALIZED) {
      dp.stateManager.transitTo(READY)
    }
    if (dpThread == null) {
      // TODO: setup context
      // operator.context = new OperatorContext(new TimeService(logManager))
      val startFuture = new CompletableFuture[Unit]()
      dpThread = dpThreadExecutor.submit(new Runnable() {
        def run(): Unit = {
          Thread.currentThread().setName(getThreadName)
          logger.info("DP thread started")
          startFuture.complete(Unit)
          try {
            runDPThreadMainLogic()
          } catch safely {
            case _: InterruptedException =>
              // dp thread will stop here
              logger.info("DP Thread exits")
            case err: Exception =>
              logger.error("DP Thread exists unexpectedly", err)
              dp.asyncRPCClient.send(
                FatalError(new WorkflowRuntimeException("DP Thread exists unexpectedly", err)),
                CONTROLLER
              )
          }
          endFuture.complete(Unit)
        }
      })
      startFuture.get()
    }
  }

  def handleActorCommand(cmd: ActorCommand): Unit = {
    cmd match {
      case Backpressure(enabled) =>
        backpressureStatus = enabled
      case _ => // no op
    }
  }

  @throws[Exception]
  private[this] def runDPThreadMainLogic(): Unit = {
    // main DP loop
    var waitingForInput = false
    while (!stopped) {
      while (internalQueue.size > 0 || waitingForInput) {
        val elem = internalQueue.take
        waitingForInput = false
        elem match {
          case WorkflowWorker.FIFOMessageElement(msg) =>
            val channel = dp.inputGateway.getChannel(msg.channel)
            channel.acceptMessage(msg)
          case WorkflowWorker.TimerBasedControlElement(control) =>
            dp.processControlPayload(ChannelID(SELF, SELF, isControl = true), control)
          case WorkflowWorker.ActorCommandElement(msg) =>
            handleActorCommand(msg)
        }
      }
      if (dp.hasUnfinishedInput || dp.hasUnfinishedOutput || dp.pauseManager.isPaused) {
        dp.inputGateway.tryPickControlChannel match {
          case Some(channel) =>
            val msg = channel.take
            dp.processControlPayload(msg.channel, msg.payload.asInstanceOf[ControlPayload])
          case None =>
            // continue processing
            if (!dp.pauseManager.isPaused && !backpressureStatus) {
              dp.continueDataProcessing()
            } else {
              waitingForInput = true
            }
        }
      } else {
        // take from input port
        if (backpressureStatus) {
          dp.inputGateway.tryPickControlChannel
        } else {
          dp.inputGateway.tryPickChannel
        } match {
          case Some(channel) =>
            val msg = channel.take
            msg.payload match {
              case payload: ControlPayload =>
                dp.processControlPayload(msg.channel, payload)
              case payload: DataPayload =>
                dp.processDataPayload(msg.channel, payload)
            }
          case None => waitingForInput = true
        }
      }
    }
  }
}
