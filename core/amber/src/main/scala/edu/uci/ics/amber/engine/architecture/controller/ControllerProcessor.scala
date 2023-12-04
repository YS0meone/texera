package edu.uci.ics.amber.engine.architecture.controller

import edu.uci.ics.amber.engine.architecture.common.{
  AkkaActorRefMappingService,
  AkkaActorService,
  AmberProcessor
}
import edu.uci.ics.amber.engine.architecture.scheduling.WorkflowScheduler
import edu.uci.ics.amber.engine.common.ambermessage.WorkflowFIFOMessage
import edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity

class ControllerProcessor(
    val workflow: Workflow,
    val controllerConfig: ControllerConfig,
    actorId: ActorVirtualIdentity,
    outputHandler: WorkflowFIFOMessage => Unit
) extends AmberProcessor(actorId, outputHandler) {

  val executionState = new ExecutionState(workflow)
  val workflowScheduler =
    new WorkflowScheduler(
      workflow.physicalPlan.regionsToSchedule.toBuffer,
      executionState,
      controllerConfig,
      asyncRPCClient
    )

  private val initializer = new ControllerAsyncRPCHandlerInitializer(this)

  @transient var controllerTimerService: ControllerTimerService = _
  def setupTimerService(controllerTimerService: ControllerTimerService): Unit = {
    this.controllerTimerService = controllerTimerService
  }

  @transient var actorService: AkkaActorService = _
  def setupActorService(akkaActorService: AkkaActorService): Unit = {
    this.actorService = akkaActorService
  }

  @transient var actorRefService: AkkaActorRefMappingService = _
  def setupActorRefService(actorRefService: AkkaActorRefMappingService): Unit = {
    this.actorRefService = actorRefService
  }

}
