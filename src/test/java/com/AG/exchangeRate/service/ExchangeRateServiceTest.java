package com.AG.exchangeRate.service;

import com.AG.exchangeRate.model.enums.Currency;
import com.AG.exchangeRate.model.enums.RateTrend;
import com.AG.exchangeRate.service.impl.ExchangeRateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;


/**
 * @author Rose
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl(exchangeRateClient, historyService);


    @Test
    public void isAscendingRateTrendTest() {
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2020, 12, 01), Map.of(Currency.USD, BigDecimal.valueOf(0.1)));
        rates.put(LocalDate.of(2020, 12, 02), Map.of(Currency.USD, BigDecimal.valueOf(0.2)));
        rates.put(LocalDate.of(2020, 12, 03), Map.of(Currency.USD, BigDecimal.valueOf(0.3)));
        rates.put(LocalDate.of(2020, 12, 04), Map.of(Currency.USD, BigDecimal.valueOf(0.4)));
        rates.put(LocalDate.of(2020, 12, 05), Map.of(Currency.USD, BigDecimal.valueOf(0.5)));

        log.info("Rates: {}", rates);

        assertEquals(RateTrend.ASCENDING, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }

    @Test
    public void isDescendingRateTrendTest() {
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2020, 12, 01), Map.of(Currency.USD, BigDecimal.valueOf(0.5)));
        rates.put(LocalDate.of(2020, 12, 02), Map.of(Currency.USD, BigDecimal.valueOf(0.4)));
        rates.put(LocalDate.of(2020, 12, 03), Map.of(Currency.USD, BigDecimal.valueOf(0.3)));
        rates.put(LocalDate.of(2020, 12, 04), Map.of(Currency.USD, BigDecimal.valueOf(0.2)));
        rates.put(LocalDate.of(2020, 12, 05), Map.of(Currency.USD, BigDecimal.valueOf(0.1)));

        assertEquals(RateTrend.DESCENDING, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }

    @Test
    public void isConstantRateTrendTest() {
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2020, 12, 01), Map.of(Currency.USD, BigDecimal.valueOf(0.10)));
        rates.put(LocalDate.of(2020, 12, 02), Map.of(Currency.USD, BigDecimal.valueOf(0.10)));
        rates.put(LocalDate.of(2020, 12, 03), Map.of(Currency.USD, BigDecimal.valueOf(0.10)));
        rates.put(LocalDate.of(2020, 12, 04), Map.of(Currency.USD, BigDecimal.valueOf(0.10)));
        rates.put(LocalDate.of(2020, 12, 05), Map.of(Currency.USD, BigDecimal.valueOf(0.10)));

        assertEquals(RateTrend.CONSTANT, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }

    @Test
    public void isUndefinedRateTrendTest() {
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2020, 12, 01), Map.of(Currency.USD, BigDecimal.valueOf(0.1)));
        rates.put(LocalDate.of(2020, 12, 02), Map.of(Currency.USD, BigDecimal.valueOf(0.3)));
        rates.put(LocalDate.of(2020, 12, 03), Map.of(Currency.USD, BigDecimal.valueOf(0.1)));
        rates.put(LocalDate.of(2020, 12, 04), Map.of(Currency.USD, BigDecimal.valueOf(0.2)));
        rates.put(LocalDate.of(2020, 12, 05), Map.of(Currency.USD, BigDecimal.valueOf(0.1)));

        assertEquals(RateTrend.UNDEFINED, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }


}