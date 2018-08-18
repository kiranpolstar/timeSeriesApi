package com.kiran.timeseries.api.controller;

import com.kiran.timeseries.api.model.ConsumptionWeeklySummary;
import com.kiran.timeseries.api.model.CustomerConsumptionSummary;
import com.kiran.timeseries.api.model.MeterConsumption;
import com.kiran.timeseries.api.service.MeterConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class TimeSeriesController {

    @Autowired
    private MeterConsumptionService meterConsumptionService;

    @PostMapping(value = "/addTimeSeries")
    public ResponseEntity addTimeSeries(@RequestBody MeterConsumption meterConsumption) {
        MeterConsumption savedMeterConsumption = meterConsumptionService.addMeterConsumption(meterConsumption);
        return ResponseEntity.status(HttpStatus.CREATED).body("MeterConsumption created with id: " + savedMeterConsumption.getId());
    }

    @GetMapping(value = "/getTimeSeries")
    public ResponseEntity getTimeSeries(@RequestParam(required = false) String meterId,
                                        @RequestParam(required = false) String customerId,
                                        @RequestParam Date fromDate,
                                        @RequestParam(required = false) Date toDate) {
        if (meterId == null && customerId == null) {
            return ResponseEntity.badRequest().body("Need either meterId or customerId");
        }
        List<MeterConsumption> consumptionList = meterConsumptionService.getMeterConsumption(meterId, customerId, fromDate, toDate);
        if (consumptionList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No consumption found for meterId : " + meterId + " customerId : " + customerId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(consumptionList);
    }

    @GetMapping(value = "/getWeeklySummaryForMeter")
    public ResponseEntity getTimeSeriesWeeklySummary(@RequestParam String meterId) {
        ConsumptionWeeklySummary consumptionWeeklySummary = meterConsumptionService.getWeeklySummaryByMeterId(meterId);
        if (consumptionWeeklySummary.getConsumptionSummaryPerWeek().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No consumption found for meterId " + meterId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(consumptionWeeklySummary);
    }

    @GetMapping(value = "/getWeeklySummaryForCustomer")
    public ResponseEntity getLastWeekSummaryByCustomerId(@RequestParam String customerId, @RequestParam Date fromDate, @RequestParam Date toDate) {
        CustomerConsumptionSummary customerConsumptionSummary = meterConsumptionService.getWeeklySummaryByCustomerId(customerId, fromDate, toDate);
        if (customerConsumptionSummary.getMeters().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No consumption found for customer " + customerId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerConsumptionSummary);
    }
}
