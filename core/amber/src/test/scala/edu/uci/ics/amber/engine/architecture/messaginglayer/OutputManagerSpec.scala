package edu.uci.ics.amber.engine.architecture.messaginglayer

import com.softwaremill.macwire.wire
import edu.uci.ics.amber.engine.architecture.sendsemantics.partitionings.OneToOnePartitioning
import edu.uci.ics.amber.engine.common.ambermessage.{
  ChannelID,
  DataFrame,
  DataPayload,
  EndOfUpstream,
  WorkflowFIFOMessage
}
import edu.uci.ics.amber.engine.common.tuple.ITuple
import edu.uci.ics.amber.engine.common.virtualidentity.{
  ActorVirtualIdentity,
  LayerIdentity,
  LinkIdentity
}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

class OutputManagerSpec extends AnyFlatSpec with MockFactory {
  private val mockHandler =
    mock[WorkflowFIFOMessage => Unit]
  private val identifier = ActorVirtualIdentity("batch producer mock")
  private val mockDataOutputPort = // scalafix:ok; need it for wiring purpose
    new NetworkOutputGateway(identifier, mockHandler)
  var counter: Int = 0

  def layerID(): LayerIdentity = {
    counter += 1
    LayerIdentity("" + counter, "" + counter, "" + counter)
  }

  def mkDataMessage(
      to: ActorVirtualIdentity,
      from: ActorVirtualIdentity,
      seq: Long,
      payload: DataPayload
  ): WorkflowFIFOMessage = {
    WorkflowFIFOMessage(ChannelID(from, to, false), seq, payload)
  }

  "OutputManager" should "aggregate tuples and output" in {
    val outputManager = wire[OutputManager]
    val tuples = Array.fill(21)(ITuple(1, 2, 3, 4, "5", 9.8))
    val fakeID = ActorVirtualIdentity("testReceiver")
    inSequence {
      (mockHandler.apply _).expects(
        mkDataMessage(fakeID, identifier, 0, DataFrame(tuples.slice(0, 10)))
      )
      (mockHandler.apply _).expects(
        mkDataMessage(fakeID, identifier, 1, DataFrame(tuples.slice(10, 20)))
      )
      (mockHandler.apply _).expects(
        mkDataMessage(fakeID, identifier, 2, DataFrame(tuples.slice(20, 21)))
      )
      (mockHandler.apply _).expects(mkDataMessage(fakeID, identifier, 3, EndOfUpstream()))
    }
    val fakeLink =
      LinkIdentity(layerID(), 0, layerID(), 0)
    val fakeReceiver = Array[ActorVirtualIdentity](fakeID)

    outputManager.addPartitionerWithPartitioning(fakeLink, OneToOnePartitioning(10, fakeReceiver))
    tuples.foreach { t =>
      outputManager.passTupleToDownstream(t, fakeLink)
    }
    outputManager.emitEndOfUpstream()
  }

  "OutputManager" should "not output tuples when there is no partitioning" in {
    val outputManager = wire[OutputManager]
    val tuples = Array.fill(21)(ITuple(1, 2, 3, 4, "5", 9.8))
    (mockHandler.apply _).expects(*).never()
    val fakeLink = LinkIdentity(layerID(), 0, layerID(), 0)
    assertThrows[Exception] {
      tuples.foreach { t =>
        outputManager.passTupleToDownstream(t, fakeLink)
      }
      outputManager.emitEndOfUpstream()
    }
  }

}
