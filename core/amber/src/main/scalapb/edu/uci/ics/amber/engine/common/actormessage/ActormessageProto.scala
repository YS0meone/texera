// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package edu.uci.ics.amber.engine.common.actormessage

object ActormessageProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    scalapb.options.ScalapbProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      edu.uci.ics.amber.engine.common.actormessage.Backpressure,
      edu.uci.ics.amber.engine.common.actormessage.CreditUpdate,
      edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage,
      edu.uci.ics.amber.engine.common.actormessage.PythonActorMessage
    )
  private lazy val ProtoBytes: _root_.scala.Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """CjJlZHUvdWNpL2ljcy9hbWJlci9lbmdpbmUvY29tbW9uL2FjdG9ybWVzc2FnZS5wcm90bxIfZWR1LnVjaS5pY3MuYW1iZXIuZ
  W5naW5lLmNvbW1vbhoVc2NhbGFwYi9zY2FsYXBiLnByb3RvIlgKDEJhY2twcmVzc3VyZRJIChNlbmFibGVfYmFja3ByZXNzdXJlG
  AEgASgIQhfiPxQSEmVuYWJsZUJhY2twcmVzc3VyZVISZW5hYmxlQmFja3ByZXNzdXJlIg4KDENyZWRpdFVwZGF0ZSLvAQoMQWN0b
  3JDb21tYW5kEmYKDGJhY2twcmVzc3VyZRgBIAEoCzItLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uQmFja3ByZXNzd
  XJlQhHiPw4SDGJhY2twcmVzc3VyZUgAUgxiYWNrcHJlc3N1cmUSZwoNY3JlZGl0X3VwZGF0ZRgCIAEoCzItLmVkdS51Y2kuaWNzL
  mFtYmVyLmVuZ2luZS5jb21tb24uQ3JlZGl0VXBkYXRlQhHiPw4SDGNyZWRpdFVwZGF0ZUgAUgxjcmVkaXRVcGRhdGVCDgoMc2Vhb
  GVkX3ZhbHVlIm4KElB5dGhvbkFjdG9yTWVzc2FnZRJYCgdwYXlsb2FkGAEgASgLMi0uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lL
  mNvbW1vbi5BY3RvckNvbW1hbmRCD+I/DBIHcGF5bG9hZPABAVIHcGF5bG9hZEIJ4j8GSABYAHgBYgZwcm90bzM="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor = {
    val javaProto = com.google.protobuf.DescriptorProtos.FileDescriptorProto.parseFrom(ProtoBytes)
    com.google.protobuf.Descriptors.FileDescriptor.buildFrom(javaProto, _root_.scala.Array(
      scalapb.options.ScalapbProto.javaDescriptor
    ))
  }
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}