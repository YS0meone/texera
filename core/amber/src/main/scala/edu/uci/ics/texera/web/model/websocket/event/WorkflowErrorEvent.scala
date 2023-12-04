package edu.uci.ics.texera.web.model.websocket.event

import edu.uci.ics.texera.web.workflowruntimestate.WorkflowFatalError

case class WorkflowErrorEvent(
    fatalErrors: Seq[WorkflowFatalError]
) extends TexeraWebSocketEvent
