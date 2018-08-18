package com.kiran.timeseries.api.repository;

import com.kiran.timeseries.api.model.MeterConsumption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface MeterConsumptionRepository extends CrudRepository<MeterConsumption, Long> {
    Iterable<MeterConsumption> findMeterConsumptionByMeterId(String meterId);

    Iterable<MeterConsumption> findFirstMeterConsumptionByMeterIdOrderByFromDateDesc(String meterId);

    Iterable<MeterConsumption> findMeterConsumptionByMeterIdAndFromDate(String meterId, Date fromDate);

    Iterable<MeterConsumption> findMeterConsumptionByMeterIdAndFromDateBetweenOrderByFromDateDesc(String meterId, Date fromDate, Date toDate);

    Iterable<MeterConsumption> findMeterConsumptionByMeterIdAndCustomerIdAndFromDate(String meterId, String customerId, Date fromDate);

    Iterable<MeterConsumption> findMeterConsumptionByMeterIdAndCustomerIdAndFromDateBetweenOrderByFromDateDesc(String meterId, String customerId, Date fromDate, Date toDate);

    Iterable<MeterConsumption> findMeterConsumptionByCustomerIdAndFromDate(String customerId, Date fromDate);

    Iterable<MeterConsumption> findMeterConsumptionByCustomerIdAndFromDateBetween(String customerId, Date fromDate, Date toDate);
}
