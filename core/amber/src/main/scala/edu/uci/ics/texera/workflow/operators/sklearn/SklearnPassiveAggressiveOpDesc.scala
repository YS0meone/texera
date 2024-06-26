package edu.uci.ics.texera.workflow.operators.sklearn

class SklearnPassiveAggressiveOpDesc extends SklearnMLOpDesc {
  override def getImportStatements = "from sklearn.linear_model import PassiveAggressiveClassifier"
  override def getUserFriendlyModelName = "Passive Aggressive"
}