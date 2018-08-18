package com.kiran.timeseries.api.model;

import java.util.List;

public class CustomerConsumptionSummary {
    private String customerId;
    private List<ConsumptionWeeklySummary> meters;

    public CustomerConsumptionSummary(String customerId, List<ConsumptionWeeklySummary> meters) {
        this.customerId = customerId;
        this.meters = meters;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<ConsumptionWeeklySummary> getMeters() {
        return meters;
    }

    public void setMeters(List<ConsumptionWeeklySummary> meters) {
        this.meters = meters;
    }
}
