package com.kiran.timeseries.api.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Map;

@Entity
@Table(name = "METER")
public class MeterConsumption {

    @Id
    @GeneratedValue
    private long id;
    private String meterId;
    private String customerId;
    private ResolutionType resolution;
    private Date fromDate;
    private Date toDate;

    @ElementCollection
    @MapKeyColumn
    private Map<String, Double> hourlyConsumption;

    public MeterConsumption() {

    }

    public MeterConsumption(long id, String meterId, String customerId, ResolutionType resolution, Date fromDate, Date toDate,
                            Map<String, Double> hourlyConsumption) {
        super();
        this.id = id;
        this.meterId = meterId;
        this.customerId = customerId;
        this.resolution = resolution;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.hourlyConsumption = hourlyConsumption;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ResolutionType getResolution() {
        return resolution;
    }

    public void setResolution(ResolutionType resolution) {
        this.resolution = resolution;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Map<String, Double> getHourlyConsumption() {
        return hourlyConsumption;
    }

    public void setHourlyConsumption(Map<String, Double> hourlyConsumption) {
        this.hourlyConsumption = hourlyConsumption;
    }

    @Override
    public String toString() {
        return "timeSeriesApi [id=" + id + ", meterId=" + meterId + ", customerId=" + customerId + ", resolution=" + resolution
                + ", fromDate=" + fromDate + ", toDate=" + toDate + ", hourlyConsumption=" + hourlyConsumption + "]";
    }


}
