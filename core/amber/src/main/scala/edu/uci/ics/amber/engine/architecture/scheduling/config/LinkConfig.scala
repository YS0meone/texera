package edu.uci.ics.amber.engine.architecture.scheduling.config
import edu.uci.ics.amber.engine.architecture.sendsemantics.partitionings.{
  BroadcastPartitioning,
  HashBasedShufflePartitioning,
  OneToOnePartitioning,
  Partitioning,
  RangeBasedShufflePartitioning,
  RoundRobinPartitioning
}
import edu.uci.ics.amber.engine.common.AmberConfig.defaultBatchSize
import edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity
import edu.uci.ics.texera.workflow.common.workflow.{
  BroadcastPartition,
  HashPartition,
  PartitionInfo,
  RangePartition,
  SinglePartition,
  UnknownPartition
}

case object LinkConfig {
  def toPartitioning(
      toWorkerIds: List[ActorVirtualIdentity],
      partitionInfo: PartitionInfo
  ): Partitioning = {
    partitionInfo match {
      case HashPartition(hashAttributeNames) =>
        HashBasedShufflePartitioning(
          defaultBatchSize,
          toWorkerIds,
          hashAttributeNames
        )

      case RangePartition(rangeAttributeNames, rangeMin, rangeMax) =>
        RangeBasedShufflePartitioning(
          defaultBatchSize,
          toWorkerIds,
          rangeAttributeNames,
          rangeMin,
          rangeMax
        )

      case SinglePartition() =>
        assert(toWorkerIds.size == 1)
        OneToOnePartitioning(defaultBatchSize, Seq(toWorkerIds.head).toSeq)

      case BroadcastPartition() =>
        BroadcastPartitioning(defaultBatchSize, toWorkerIds)

      case UnknownPartition() =>
        RoundRobinPartitioning(defaultBatchSize, toWorkerIds)

      case _ =>
        throw new UnsupportedOperationException()

    }
  }
}

case class LinkConfig(
    channelConfigs: List[ChannelConfig],
    partitioning: Partitioning
)
