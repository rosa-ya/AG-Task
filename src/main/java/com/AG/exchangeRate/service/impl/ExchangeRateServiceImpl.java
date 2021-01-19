package com.AG.exchangeRate.service.impl;

import com.AG.exchangeRate.model.dto.ExchangeRateAPIResponse;
import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.dto.HistoryExchangeRateAPIResponse;
import com.AG.exchangeRate.model.enums.Currency;
import com.AG.exchangeRate.model.enums.RateTrend;
import com.AG.exchangeRate.service.ExchangeRateClient;
import com.AG.exchangeRate.service.ExchangeRateService;
import com.AG.exchangeRate.service.HistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Rose
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {


    ExchangeRateClient exchangeRateClient;

    HistoryService historyService;


    /**
     * This method is used to communicate with the API of https://exchangeratesapi.io/ for getting exchange rate
     * and calculate average of the five days before the requested date
     * and determine the exchange rate trend
     * @param date           specifies the requested date for exchange rate
     * @param baseCurrency
     * @param targetCurrency
     * @return ExchangeRateResponseDto
     */
    @Override
    public ExchangeRateResponseDto getExchangeRateForGivenDateAndCurrencies(String date, Currency baseCurrency, Currency targetCurrency) {

        ResponseEntity<ExchangeRateAPIResponse> exchangeRateAPIResponseResponseEntity = exchangeRateClient.getExchangeRateForDate(date, baseCurrency, targetCurrency);

        LocalDate startDate = LocalDate.parse(date).minusDays(5);
        ResponseEntity<HistoryExchangeRateAPIResponse> historical = exchangeRateClient.getHistoricalExchangeRate(startDate.toString(), date, baseCurrency, targetCurrency);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        ExchangeRateResponseDto exchangeRateResponseDto = ExchangeRateResponseDto.builder()
                .exchangeRate(exchangeRateAPIResponseResponseEntity.getBody().getRates().get(targetCurrency))
                .average(calculateAverage(historical.getBody(), targetCurrency))
                .trend(determineRateTrend(historical.getBody().getRates(), targetCurrency))
                .base(baseCurrency)
                .target(targetCurrency)
                .creationDate(new Date())
                .exchangeRateStartDate(Date.from(startDate.atStartOfDay(defaultZoneId).toInstant()))
                .build();

        historyService.saveExchangeRate(exchangeRateResponseDto);
        return exchangeRateResponseDto;
    }

    /**
     * This method is used to calculate average of the five days before the requested date
     */
    @Override
    public BigDecimal calculateAverage(HistoryExchangeRateAPIResponse historyExchangeRateAPIResponse, Currency target) {
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = historyExchangeRateAPIResponse.getRates();
        LocalDate end = historyExchangeRateAPIResponse.getEnd_at();
        LocalDate start = historyExchangeRateAPIResponse.getStart_at();

        BigDecimal sum = BigDecimal.valueOf(0.0);
        int count = 0;

        log.info("Calculating the average of the five days before the requested date {} ", end);
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            if (rates.containsKey(date)) {
                count++;
                sum = sum.add(rates.get(date).get(target));
            }
        }
        BigDecimal average = ((count == 0) ? BigDecimal.valueOf(0.0) : sum.divide(BigDecimal.valueOf(count), 5, RoundingMode.HALF_UP));

        log.info("sum = {}. average = {}", sum, average);

        return average;
    }

    /**
     * This method is used to determine the exchange rate trend
     */
    @Override
    public RateTrend determineRateTrend(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target) {
        if (isAscending(rates, target)) {
            return RateTrend.ASCENDING;
        } else if (isDescending(rates, target)) {
            return RateTrend.DESCENDING;
        } else if (isConstant(rates, target)) {
            return RateTrend.CONSTANT;
        }
        return RateTrend.UNDEFINED;
    }

    /**
     * This method is used to determine the exchange rate trend is ascending or not
     * When the exchange rates in the last five days are in strictly ascending order
     */
    private final boolean isAscending(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target) {
        boolean isAscending = true;
        Iterator<Map.Entry<LocalDate, Map<Currency, BigDecimal>>> entries = rates.entrySet().iterator();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> entry = entries.next();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> next;

        while (isAscending && entries.hasNext()) {
            next = entries.next();

            log.info("Calculating trend between {} and {}. ", entry.getValue(), next.getValue());
            if (entry.getValue().get(target).compareTo(next.getValue().get(target)) >= 0) {
                isAscending = false;
            }
            entry = next;
        }
        log.info("The trend is{} ascending.", ((isAscending) ? "" : " not"));
        return isAscending;
    }

    /**
     * This method is used to determine the exchange rate trend is descending or not
     * When the exchange rates in the last five days are in strictly descending order
     */
    private final boolean isDescending(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target) {
        boolean isDescending = true;
        Iterator<Map.Entry<LocalDate, Map<Currency, BigDecimal>>> entries = rates.entrySet().iterator();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> entry = entries.next();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> next;

        while (isDescending && entries.hasNext()) {
            next = entries.next();
            if (entry.getValue().get(target).compareTo(next.getValue().get(target)) <= 0) {
                isDescending = false;
            }
            entry = next;
        }
        log.info("The trend is{} descending.", ((isDescending) ? "" : " not"));

        return isDescending;
    }

    /**
     * This method is used to determine the exchange rate trend is constant or not
     * When the exchange rates in the last five days are the same
     */
    private final boolean isConstant(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target) {
        boolean isConstant = true;
        Iterator<Map.Entry<LocalDate, Map<Currency, BigDecimal>>> entries = rates.entrySet().iterator();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> entry = entries.next();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> next;

        while (isConstant && entries.hasNext()) {
            next = entries.next();
            if (entry.getValue().get(target).compareTo(next.getValue().get(target)) != 0) {
                isConstant = false;
            }
            entry = next;
        }
        log.info("The trend is{} constant.", ((isConstant) ? "" : " not"));

        return isConstant;
    }


}
