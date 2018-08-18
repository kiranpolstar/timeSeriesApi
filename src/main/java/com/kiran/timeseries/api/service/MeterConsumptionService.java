package com.kiran.timeseries.api.service;

import com.kiran.timeseries.api.model.ConsumptionWeeklySummary;
import com.kiran.timeseries.api.model.CustomerConsumptionSummary;
import com.kiran.timeseries.api.model.MeterConsumption;
import com.kiran.timeseries.api.repository.MeterConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeterConsumptionService {
    private static final Logger log = LoggerFactory.getLogger(MeterConsumptionService.class);

    @Autowired
    private MeterConsumptionRepository meterConsumptionRepository;

    public MeterConsumption addMeterConsumption(MeterConsumption meterConsumption) {
        List<MeterConsumption> savedMeterConsumption = (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByMeterIdAndFromDate(meterConsumption.getMeterId(), meterConsumption.getFromDate());
        if (savedMeterConsumption.isEmpty()) {
            return meterConsumptionRepository.save(meterConsumption);
        } else {//update the existing record
            BeanUtils.copyProperties(meterConsumption, savedMeterConsumption.get(0), "id");
            return meterConsumptionRepository.save(savedMeterConsumption.get(0));
        }
    }

    public List<MeterConsumption> getMeterConsumption(String meterId, String customerId,
                                                      Date fromDate, Date toDate) {
        if (customerId != null && meterId != null) {
            if (toDate != null) {
                return (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByMeterIdAndCustomerIdAndFromDateBetweenOrderByFromDateDesc(meterId, customerId, fromDate, toDate);
            }
            return (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByMeterIdAndCustomerIdAndFromDate(meterId, customerId, fromDate);
        } else if (customerId != null) {
            if (toDate != null) {
                return (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByCustomerIdAndFromDateBetween(customerId, fromDate, toDate);
            }
            return (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByCustomerIdAndFromDate(customerId, fromDate);
        }
        if (toDate != null) {
            return (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByMeterIdAndFromDateBetweenOrderByFromDateDesc(meterId, fromDate, toDate);
        }
        return (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByMeterIdAndFromDate(meterId, fromDate);

    }

    public CustomerConsumptionSummary getWeeklySummaryByCustomerId(String customerId, Date fromdate, Date toDate) {
        List<ConsumptionWeeklySummary> weeklyConsumptionSummaryForAllMeters = new ArrayList<>();
        List<MeterConsumption> consumptionList = (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByCustomerIdAndFromDateBetween(customerId, fromdate, toDate);
        Map<String, List<MeterConsumption>> consumptionListGroupedByMeterId = consumptionList.stream().collect(Collectors.groupingBy(MeterConsumption::getMeterId));
        consumptionListGroupedByMeterId.forEach((key, value) -> weeklyConsumptionSummaryForAllMeters.add(getConsumptionWeeklySummary(key, value)));
        return new CustomerConsumptionSummary(customerId, weeklyConsumptionSummaryForAllMeters);
    }

    public ConsumptionWeeklySummary getWeeklySummaryByMeterId(String meterId) {
        List<MeterConsumption> consumptionList = (List<MeterConsumption>) meterConsumptionRepository.findMeterConsumptionByMeterId(meterId);

        return getConsumptionWeeklySummary(meterId, consumptionList);
    }

    private ConsumptionWeeklySummary getConsumptionWeeklySummary(String meterId, List<MeterConsumption> consumptionList) {
        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfYear();
        Map<Integer, Double> summaryMap = new HashMap<>();
        consumptionList.stream().collect(Collectors.groupingBy(consumption ->
                consumption.getFromDate().toLocalDate().get(weekOfYear), LinkedHashMap::new, Collectors.toList())).forEach((week, consumptions) -> {
            log.info("Week " + week + ":");
            summaryMap.put(week, consumptions.stream().map(c -> c.getHourlyConsumption().values().stream().mapToDouble(Double::doubleValue).sum()).mapToDouble(Double::doubleValue).sum());
            log.info("Week summary" + consumptions.stream().map(c -> c.getHourlyConsumption().values().stream().mapToDouble(Double::doubleValue).sum()).mapToDouble(Double::doubleValue).sum());
        });
        return new ConsumptionWeeklySummary(meterId, summaryMap);
    }
//    public Meter getMeterById(long id) {
//        return meterRepository.findMeterByMeterId(id);
//    }
//
//    public Page<Meter> getAllMeters(Integer page, Integer size) {
//        Page pageOfMeters = meterRepository.findAll(new PageRequest(page, size));
//        // example of adding to the /metrics
////        if (size > 50) {
////            counterService.increment("Khoubyari.HotelService.getAll.largePayload");
////        }
//        return pageOfMeters;
//    }
//
//    public Meter getTimeSeries(Date inputDate, String customerId) {
//
//
//        List<Meter> meterList = (List<Meter>) meterRepository.findAll();
////		Map<String, Double> valuesMap = new HashMap<String, Double>();
//
////		for(Meter m : meterList) {
//////			String fromDate = m.getFromDate();
////
////			LocalDate date1 = LocalDate.parse(fromDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
////			String formattedFromDate = date1.toString();
////
////			if(inputDate.equals(formattedFromDate) && customerId.equals(m.getCustomerId())) {
//////				valuesMap.putAll(m.getHourlyConsumption());
////				return m.getHourlyConsumption();
////			}
////		}
//
//        return null;
//    }
//
//    public Meter getTimeSeriesByMeterId(String meterId) {
//        List<Meter> meterList = (List<Meter>) meterRepository.findFirstMeterByMeterIdOrderByFromDateDesc(meterId);
//        return meterList.get(0);
//    }
}
