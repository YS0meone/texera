package edu.uci.ics.amber.error

import com.google.protobuf.timestamp.Timestamp
import edu.uci.ics.amber.engine.architecture.worker.controlcommands.ConsoleMessage
import edu.uci.ics.amber.engine.architecture.worker.controlcommands.ConsoleMessageType.ERROR
import edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity

import java.time.Instant
import scala.util.control.ControlThrowable

object ErrorUtils {

  /** A helper function for catching all throwable except some special scala internal throwable.
    * reference: https://www.sumologic.com/blog/why-you-should-never-catch-throwable-in-scala/
    * @param handler
    * @tparam T
    * @return
    */
  def safely[T](handler: PartialFunction[Throwable, T]): PartialFunction[Throwable, T] = {
    case ex: ControlThrowable => throw ex
    // case ex: OutOfMemoryError (Assorted other nasty exceptions you don't want to catch)
    // If it's an exception they handle, pass it on
    case ex: Throwable if handler.isDefinedAt(ex) => handler(ex)
    // If they didn't handle it, rethrow automatically
  }

  def mkConsoleMessage(actorId: ActorVirtualIdentity, err: Throwable): ConsoleMessage = {
    val source =
      "(" + err.getStackTrace.head.getFileName + ":" + err.getStackTrace.head.getLineNumber + ")"
    val title = err.toString
    val message = err.getStackTrace.mkString("\n")
    ConsoleMessage(actorId.name, Timestamp(Instant.now), ERROR, source, title, message)
  }

  def getStackTraceWithAllCauses(err: Throwable): String = {
    val message = err.toString + "\n" + err.getStackTrace.mkString("\n")
    if (err.getCause != null) {
      message + "\n\nCaused by:\n" + getStackTraceWithAllCauses(err.getCause)
    } else {
      message
    }
  }

}
