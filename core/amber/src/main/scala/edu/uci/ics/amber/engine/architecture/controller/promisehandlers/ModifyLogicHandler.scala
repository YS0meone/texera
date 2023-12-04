package edu.uci.ics.amber.engine.architecture.controller.promisehandlers

import com.twitter.util.Future
import edu.uci.ics.amber.engine.architecture.controller.ControllerAsyncRPCHandlerInitializer
import edu.uci.ics.amber.engine.architecture.controller.promisehandlers.ConsoleMessageHandler.ConsoleMessageTriggered
import edu.uci.ics.amber.engine.architecture.controller.promisehandlers.ModifyLogicHandler.ModifyLogic
import edu.uci.ics.amber.engine.architecture.deploysemantics.layer.OpExecConfig
import edu.uci.ics.amber.engine.architecture.pythonworker.promisehandlers.ModifyPythonOperatorLogicHandler.ModifyPythonOperatorLogic
import edu.uci.ics.amber.engine.architecture.worker.promisehandlers.ModifyOperatorLogicHandler.WorkerModifyLogic
import edu.uci.ics.amber.engine.common.rpc.AsyncRPCServer.ControlCommand
import edu.uci.ics.amber.error.ErrorUtils.mkConsoleMessage
import edu.uci.ics.texera.workflow.common.operators.StateTransferFunc

object ModifyLogicHandler {

  final case class ModifyLogic(newOp: OpExecConfig, stateTransferFunc: Option[StateTransferFunc])
      extends ControlCommand[Unit]
}

/** retry the execution of the entire workflow
  *
  * possible sender: controller, client
  */
trait ModifyLogicHandler {
  this: ControllerAsyncRPCHandlerInitializer =>

  registerHandler { (msg: ModifyLogic, sender) =>
    {
      val operator = cp.workflow.physicalPlan.operatorMap(msg.newOp.id)
      val opExecution = cp.executionState.getOperatorExecution(msg.newOp.id)
      val workerCommand = if (operator.isPythonOperator) {
        ModifyPythonOperatorLogic(
          msg.newOp.getPythonCode,
          isSource = operator.isSourceOperator
        )
      } else {
        WorkerModifyLogic(msg.newOp, msg.stateTransferFunc)
      }
      Future
        .collect(opExecution.getBuiltWorkerIds.map { worker =>
          send(workerCommand, worker).onFailure((err: Throwable) => {
            logger.error("Failure when performing reconfiguration", err)
            // report error to frontend
            val errorEvt = ConsoleMessageTriggered(mkConsoleMessage(actorId, err))
            sendToClient(errorEvt)
          })
        }.toSeq)
        .unit
    }
  }
}
