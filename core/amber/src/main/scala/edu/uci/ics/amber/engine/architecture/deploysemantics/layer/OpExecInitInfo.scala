package edu.uci.ics.amber.engine.architecture.deploysemantics.layer

import edu.uci.ics.amber.engine.common.IOperatorExecutor

object OpExecInitInfo {

  type OpExecFunc = ((Int, OpExecConfig)) => IOperatorExecutor
  type JavaOpExecFunc = java.util.function.Function[(Int, OpExecConfig), IOperatorExecutor]
    with java.io.Serializable

  def apply(code: String): OpExecInitInfo = OpExecInitInfoWithCode(_ => code)
  def apply(opExecFunc: OpExecFunc): OpExecInitInfo = OpExecInitInfoWithFunc(opExecFunc)
  def apply(opExecFunc: JavaOpExecFunc): OpExecInitInfo =
    OpExecInitInfoWithFunc(x => opExecFunc.apply(x))
}

/**
  * Information regarding initializing an operator executor instance
  * it could be two cases:
  *   - OpExecInitInfoWithFunc:
  *       A function to create an operator executor instance, with parameters:
  *       1) worker index, 2) this worker layer object;
  *   - OpExecInitInfoWithCode:
  *       A function returning the code string that to be compiled in a virtual machine.
  */
sealed trait OpExecInitInfo

// only for Python right now
// TODO: add language type into this class
final case class OpExecInitInfoWithCode(codeGen: ((Int, OpExecConfig)) => String)
    extends OpExecInitInfo
final case class OpExecInitInfoWithFunc(opGen: ((Int, OpExecConfig)) => IOperatorExecutor)
    extends OpExecInitInfo
