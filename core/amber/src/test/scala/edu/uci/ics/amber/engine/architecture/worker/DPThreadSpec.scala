package edu.uci.ics.amber.engine.architecture.worker

import edu.uci.ics.amber.engine.architecture.deploysemantics.layer.OpExecInitInfo
import edu.uci.ics.amber.engine.architecture.deploysemantics.{PhysicalLink, PhysicalOp}
import edu.uci.ics.amber.engine.architecture.logreplay.ReplayLogManager
import edu.uci.ics.amber.engine.architecture.logreplay.storage.ReplayLogStorage
import edu.uci.ics.amber.engine.architecture.messaginglayer.WorkerTimerService
import edu.uci.ics.amber.engine.architecture.scheduling.config.{OperatorConfig, WorkerConfig}
import edu.uci.ics.amber.engine.architecture.worker.WorkflowWorker.{
  DPInputQueueElement,
  FIFOMessageElement,
  TimerBasedControlElement
}
import edu.uci.ics.amber.engine.architecture.worker.promisehandlers.PauseHandler.PauseWorker
import edu.uci.ics.amber.engine.architecture.worker.promisehandlers.ResumeHandler.ResumeWorker
import edu.uci.ics.amber.engine.common.ambermessage.{ChannelID, DataFrame, WorkflowFIFOMessage}
import edu.uci.ics.amber.engine.common.rpc.AsyncRPCClient.ControlInvocation
import edu.uci.ics.amber.engine.common.tuple.ITuple
import edu.uci.ics.amber.engine.common.virtualidentity.{
  ActorVirtualIdentity,
  OperatorIdentity,
  PhysicalOpIdentity
}
import edu.uci.ics.texera.workflow.common.WorkflowContext.{
  DEFAULT_EXECUTION_ID,
  DEFAULT_WORKFLOW_ID
}
import edu.uci.ics.texera.workflow.common.operators.OperatorExecutor
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

import java.net.URI
import java.util.concurrent.LinkedBlockingQueue

class DPThreadSpec extends AnyFlatSpec with MockFactory {

  private val identifier: ActorVirtualIdentity = ActorVirtualIdentity("DP mock")
  private val senderID: ActorVirtualIdentity = ActorVirtualIdentity("mock sender")
  private val dataChannelID = ChannelID(senderID, identifier, isControl = false)
  private val controlChannelID = ChannelID(senderID, identifier, isControl = true)
  private val operator = mock[OperatorExecutor]
  private val operatorIdentity = OperatorIdentity("testOperator")
  private val physicalOp1 = PhysicalOp(
    id = PhysicalOpIdentity(operatorIdentity, "1st-physical-op"),
    workflowId = DEFAULT_WORKFLOW_ID,
    executionId = DEFAULT_EXECUTION_ID,
    opExecInitInfo = null
  )
  private val physicalOp2 = PhysicalOp(
    id = PhysicalOpIdentity(operatorIdentity, "1st-physical-op"),
    workflowId = DEFAULT_WORKFLOW_ID,
    executionId = DEFAULT_EXECUTION_ID,
    opExecInitInfo = null
  )
  private val mockLink = PhysicalLink(physicalOp1, 0, physicalOp2, 0)

  private val physicalOp = PhysicalOp
    .oneToOnePhysicalOp(
      workflowId = DEFAULT_WORKFLOW_ID,
      executionId = DEFAULT_EXECUTION_ID,
      logicalOpId = operatorIdentity,
      OpExecInitInfo((_, _, _) => operator)
    )
    .copy(
      inputPortToLinkIdMapping = Map(0 -> List(mockLink.id)),
      outputPortToLinkIdMapping = Map(0 -> List(mockLink.id))
    )
  private val tuples: Array[ITuple] = (0 until 5000).map(ITuple(_)).toArray
  private val logStorage = ReplayLogStorage.getLogStorage(None)
  private val logManager: ReplayLogManager =
    ReplayLogManager.createLogManager(logStorage, "none", x => {})

  "DP Thread" should "handle pause/resume during processing" in {
    val dp = new DataProcessor(identifier, x => {})
    dp.initOperator(0, physicalOp, OperatorConfig(List(WorkerConfig(identifier))), Iterator.empty)
    val inputQueue = new LinkedBlockingQueue[DPInputQueueElement]()
    dp.registerInput(senderID, mockLink.id)
    dp.adaptiveBatchingMonitor = mock[WorkerTimerService]
    (dp.adaptiveBatchingMonitor.resumeAdaptiveBatching _).expects().anyNumberOfTimes()
    val dpThread = new DPThread(identifier, dp, logManager, inputQueue)
    dpThread.start()
    tuples.foreach { x =>
      (operator.processTuple _).expects(Left(x), 0, dp.pauseManager, dp.asyncRPCClient)
    }
    val message = WorkflowFIFOMessage(dataChannelID, 0, DataFrame(tuples))
    inputQueue.put(FIFOMessageElement(message))
    inputQueue.put(
      TimerBasedControlElement(ControlInvocation(0, PauseWorker()))
    )
    Thread.sleep(1000)
    assert(dp.pauseManager.isPaused)
    inputQueue.put(TimerBasedControlElement(ControlInvocation(1, ResumeWorker())))
    Thread.sleep(1000)
    while (dp.hasUnfinishedInput) {
      Thread.sleep(100)
    }
  }

  "DP Thread" should "handle pause/resume using fifo messages" in {
    val dp = new DataProcessor(identifier, x => {})
    dp.initOperator(0, physicalOp, OperatorConfig(List(WorkerConfig(identifier))), Iterator.empty)
    val inputQueue = new LinkedBlockingQueue[DPInputQueueElement]()
    dp.registerInput(senderID, mockLink.id)
    dp.adaptiveBatchingMonitor = mock[WorkerTimerService]
    (dp.adaptiveBatchingMonitor.resumeAdaptiveBatching _).expects().anyNumberOfTimes()
    val dpThread = new DPThread(identifier, dp, logManager, inputQueue)
    dpThread.start()
    tuples.foreach { x =>
      (operator.processTuple _).expects(Left(x), 0, dp.pauseManager, dp.asyncRPCClient)
    }
    val message = WorkflowFIFOMessage(dataChannelID, 0, DataFrame(tuples))
    val pauseControl = WorkflowFIFOMessage(controlChannelID, 0, ControlInvocation(0, PauseWorker()))
    val resumeControl =
      WorkflowFIFOMessage(controlChannelID, 1, ControlInvocation(1, ResumeWorker()))
    inputQueue.put(FIFOMessageElement(message))
    inputQueue.put(
      FIFOMessageElement(pauseControl)
    )
    Thread.sleep(1000)
    assert(dp.pauseManager.isPaused)
    inputQueue.put(FIFOMessageElement(resumeControl))
    Thread.sleep(1000)
    while (dp.hasUnfinishedInput) {
      Thread.sleep(100)
    }
  }

  "DP Thread" should "handle multiple batches from multiple sources" in {
    val dp = new DataProcessor(identifier, x => {})
    dp.initOperator(0, physicalOp, OperatorConfig(List(WorkerConfig(identifier))), Iterator.empty)
    val inputQueue = new LinkedBlockingQueue[DPInputQueueElement]()
    val anotherSender = ActorVirtualIdentity("another")
    dp.registerInput(senderID, mockLink.id)
    dp.registerInput(anotherSender, mockLink.id)
    dp.adaptiveBatchingMonitor = mock[WorkerTimerService]
    (dp.adaptiveBatchingMonitor.resumeAdaptiveBatching _).expects().anyNumberOfTimes()
    val dpThread = new DPThread(identifier, dp, logManager, inputQueue)
    dpThread.start()
    tuples.foreach { x =>
      (operator.processTuple _).expects(Left(x), 0, dp.pauseManager, dp.asyncRPCClient)
    }
    val dataChannelID2 = ChannelID(anotherSender, identifier, isControl = false)
    val message1 = WorkflowFIFOMessage(dataChannelID, 0, DataFrame(tuples.slice(0, 100)))
    val message2 = WorkflowFIFOMessage(dataChannelID, 1, DataFrame(tuples.slice(100, 200)))
    val message3 = WorkflowFIFOMessage(dataChannelID2, 0, DataFrame(tuples.slice(300, 1000)))
    val message4 = WorkflowFIFOMessage(dataChannelID, 2, DataFrame(tuples.slice(200, 300)))
    val message5 = WorkflowFIFOMessage(dataChannelID2, 1, DataFrame(tuples.slice(1000, 5000)))
    inputQueue.put(FIFOMessageElement(message1))
    inputQueue.put(FIFOMessageElement(message2))
    inputQueue.put(FIFOMessageElement(message3))
    inputQueue.put(FIFOMessageElement(message4))
    inputQueue.put(FIFOMessageElement(message5))
    Thread.sleep(1000)
    while (dp.hasUnfinishedInput) {
      Thread.sleep(100)
    }
  }

  "DP Thread" should "write determinant logs to local storage while processing" in {
    val dp = new DataProcessor(identifier, x => {})
    dp.initOperator(0, physicalOp, OperatorConfig(List(WorkerConfig(identifier))), Iterator.empty)
    val inputQueue = new LinkedBlockingQueue[DPInputQueueElement]()
    val anotherSender = ActorVirtualIdentity("another")
    dp.registerInput(senderID, mockLink.id)
    dp.registerInput(anotherSender, mockLink.id)
    dp.adaptiveBatchingMonitor = mock[WorkerTimerService]
    (dp.adaptiveBatchingMonitor.resumeAdaptiveBatching _).expects().anyNumberOfTimes()
    val logStorage = ReplayLogStorage.getLogStorage(Some(new URI("file:///recovery-logs/tmp")))
    logStorage.deleteStorage()
    val logManager: ReplayLogManager =
      ReplayLogManager.createLogManager(logStorage, "tmpLog", x => {})
    val dpThread = new DPThread(identifier, dp, logManager, inputQueue)
    dpThread.start()
    tuples.foreach { x =>
      (operator.processTuple _).expects(Left(x), 0, dp.pauseManager, dp.asyncRPCClient)
    }
    val dataChannelID2 = ChannelID(anotherSender, identifier, isControl = false)
    val message1 = WorkflowFIFOMessage(dataChannelID, 0, DataFrame(tuples.slice(0, 100)))
    val message2 = WorkflowFIFOMessage(dataChannelID, 1, DataFrame(tuples.slice(100, 200)))
    val message3 = WorkflowFIFOMessage(dataChannelID2, 0, DataFrame(tuples.slice(300, 1000)))
    val message4 = WorkflowFIFOMessage(dataChannelID, 2, DataFrame(tuples.slice(200, 300)))
    val message5 = WorkflowFIFOMessage(dataChannelID2, 1, DataFrame(tuples.slice(1000, 5000)))
    inputQueue.put(FIFOMessageElement(message1))
    inputQueue.put(FIFOMessageElement(message2))
    inputQueue.put(FIFOMessageElement(message3))
    Thread.sleep(1000)
    inputQueue.put(FIFOMessageElement(message4))
    inputQueue.put(FIFOMessageElement(message5))
    Thread.sleep(1000)
    while (logManager.getStep < 4999) {
      Thread.sleep(100)
    }
    logManager.sendCommitted(null) // drain in-mem records to flush
    logManager.terminate()
    val logs = logStorage.getReader("tmpLog").mkLogRecordIterator().toArray
    logStorage.deleteStorage()
    assert(logs.length > 1)
  }

}
