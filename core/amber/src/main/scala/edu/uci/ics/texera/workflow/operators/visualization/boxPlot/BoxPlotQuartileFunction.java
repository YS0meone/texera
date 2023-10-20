package edu.uci.ics.texera.workflow.operators.visualization.boxPlot;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BoxPlotQuartileFunction {
    LINEAR("linear"),
    INCLUSIVE("inclusive"),
    EXCLUSIVE("exclusive");
    private final String quartiletype;

    BoxPlotQuartileFunction(String quartiletype) {
        this.quartiletype = quartiletype;
    }

    @JsonValue
    public String getQuartiletype() {
        return this.quartiletype;
    }
}