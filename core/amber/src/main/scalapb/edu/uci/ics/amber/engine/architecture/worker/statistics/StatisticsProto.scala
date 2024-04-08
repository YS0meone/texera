// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package edu.uci.ics.amber.engine.architecture.worker.statistics

object StatisticsProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    edu.uci.ics.amber.engine.common.workflow.WorkflowProto,
    scalapb.options.ScalapbProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      edu.uci.ics.amber.engine.architecture.worker.statistics.PortTupleCountMapping,
      edu.uci.ics.amber.engine.architecture.worker.statistics.WorkerStatistics,
      edu.uci.ics.amber.engine.architecture.worker.statistics.WorkerMetrics
    )
  private lazy val ProtoBytes: _root_.scala.Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """Cj1lZHUvdWNpL2ljcy9hbWJlci9lbmdpbmUvYXJjaGl0ZWN0dXJlL3dvcmtlci9zdGF0aXN0aWNzLnByb3RvEixlZHUudWNpL
  mljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLndvcmtlchouZWR1L3VjaS9pY3MvYW1iZXIvZW5naW5lL2NvbW1vbi93b3JrZ
  mxvdy5wcm90bxoVc2NhbGFwYi9zY2FsYXBiLnByb3RvIp4BChVQb3J0VHVwbGVDb3VudE1hcHBpbmcSUwoHcG9ydF9pZBgBIAEoC
  zItLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uUG9ydElkZW50aXR5QgviPwgSBnBvcnRJZFIGcG9ydElkEjAKC3R1c
  GxlX2NvdW50GAIgASgDQg/iPwwSCnR1cGxlQ291bnRSCnR1cGxlQ291bnQi8AMKEFdvcmtlclN0YXRpc3RpY3MShQEKEWlucHV0X
  3R1cGxlX2NvdW50GAEgAygLMkMuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS53b3JrZXIuUG9ydFR1cGxlQ
  291bnRNYXBwaW5nQhTiPxESD2lucHV0VHVwbGVDb3VudFIPaW5wdXRUdXBsZUNvdW50EogBChJvdXRwdXRfdHVwbGVfY291bnQYA
  iADKAsyQy5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLndvcmtlci5Qb3J0VHVwbGVDb3VudE1hcHBpbmdCF
  eI/EhIQb3V0cHV0VHVwbGVDb3VudFIQb3V0cHV0VHVwbGVDb3VudBJJChRkYXRhX3Byb2Nlc3NpbmdfdGltZRgDIAEoA0IX4j8UE
  hJkYXRhUHJvY2Vzc2luZ1RpbWVSEmRhdGFQcm9jZXNzaW5nVGltZRJSChdjb250cm9sX3Byb2Nlc3NpbmdfdGltZRgEIAEoA0Ia4
  j8XEhVjb250cm9sUHJvY2Vzc2luZ1RpbWVSFWNvbnRyb2xQcm9jZXNzaW5nVGltZRIqCglpZGxlX3RpbWUYBSABKANCDeI/ChIIa
  WRsZVRpbWVSCGlkbGVUaW1lIooCCg1Xb3JrZXJNZXRyaWNzEnEKDHdvcmtlcl9zdGF0ZRgBIAEoDjI5LmVkdS51Y2kuaWNzLmFtY
  mVyLmVuZ2luZS5hcmNoaXRlY3R1cmUud29ya2VyLldvcmtlclN0YXRlQhPiPxASC3dvcmtlclN0YXRl8AEBUgt3b3JrZXJTdGF0Z
  RKFAQoRd29ya2VyX3N0YXRpc3RpY3MYAiABKAsyPi5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLndvcmtlc
  i5Xb3JrZXJTdGF0aXN0aWNzQhjiPxUSEHdvcmtlclN0YXRpc3RpY3PwAQFSEHdvcmtlclN0YXRpc3RpY3MqUwoLV29ya2VyU3Rhd
  GUSEQoNVU5JTklUSUFMSVpFRBAAEgkKBVJFQURZEAESCwoHUlVOTklORxACEgoKBlBBVVNFRBADEg0KCUNPTVBMRVRFRBAEQgniP
  wZIAFgAeAFiBnByb3RvMw=="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor = {
    val javaProto = com.google.protobuf.DescriptorProtos.FileDescriptorProto.parseFrom(ProtoBytes)
    com.google.protobuf.Descriptors.FileDescriptor.buildFrom(javaProto, _root_.scala.Array(
      edu.uci.ics.amber.engine.common.workflow.WorkflowProto.javaDescriptor,
      scalapb.options.ScalapbProto.javaDescriptor
    ))
  }
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}