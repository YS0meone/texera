package edu.uci.ics.texera.workflow.operators.udf.python

import com.google.common.base.Preconditions
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle
import edu.uci.ics.texera.workflow.common.metadata.{
  InputPort,
  OperatorGroupConstants,
  OperatorInfo,
  OutputPort
}
import edu.uci.ics.texera.workflow.common.operators.PythonOperatorDescriptor
import edu.uci.ics.texera.workflow.common.tuple.schema.{OperatorSchemaInfo, Schema}

class PythonTableReducerOpDesc extends PythonOperatorDescriptor {
  @JsonSchemaTitle("Output columns")
  var lambdaAttributeUnits: List[LambdaAttributeUnit] = List()

  override def numWorkers(): Int = 1

  override def getOutputSchema(schemas: Array[Schema]): Schema = {
    Preconditions.checkArgument(lambdaAttributeUnits.nonEmpty)
    val outputSchemaBuilder = Schema.newBuilder
    for (unit <- lambdaAttributeUnits) {
      outputSchemaBuilder.add(unit.attributeName, unit.attributeType)
    }
    outputSchemaBuilder.build()
  }

  override def operatorInfo: OperatorInfo =
    OperatorInfo(
      "Python Table Reducer",
      "Reduce Table to Tuple",
      OperatorGroupConstants.UDF_GROUP,
      inputPorts = List(InputPort()),
      outputPorts = List(OutputPort())
    )

  override def generatePythonCode(operatorSchemaInfo: OperatorSchemaInfo): String = {
    var outputTable = "{"
    for (unit <- lambdaAttributeUnits) {
      outputTable += s"""\"${unit.attributeName}\":${unit.expression},"""
    }
    outputTable += "}"
    s"""
from pytexera import *
class ProcessTableOperator(UDFTableOperator):
    @overrides
    def process_table(self, table: Table, port: int) -> Iterator[Optional[TableLike]]:
        yield $outputTable
"""
  }
}
