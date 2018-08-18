package com.kiran.timeseries.api.model;

import java.util.Map;

public class ConsumptionWeeklySummary {
    private String meterId;
    private Map<Integer, Double> consumptionSummaryPerWeek;

    public ConsumptionWeeklySummary(String meterId, Map<Integer, Double> consumptionSummaryPerWeek) {
        this.meterId = meterId;
        this.consumptionSummaryPerWeek = consumptionSummaryPerWeek;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public Map<Integer, Double> getConsumptionSummaryPerWeek() {
        return consumptionSummaryPerWeek;
    }

    public void setConsumptionSummaryPerWeek(Map<Integer, Double> consumptionSummaryPerWeek) {
        this.consumptionSummaryPerWeek = consumptionSummaryPerWeek;
    }
}
