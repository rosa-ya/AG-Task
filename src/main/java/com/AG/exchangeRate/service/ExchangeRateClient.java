package com.AG.exchangeRate.service;


import com.AG.exchangeRate.model.dto.ExchangeRateAPIResponse;
import com.AG.exchangeRate.model.dto.HistoryExchangeRateAPIResponse;
import com.AG.exchangeRate.model.enums.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Rose
 * This interface communicate with the API of https://exchangeratesapi.io/
 * FeignClient is a library for creating REST API clients in a declarative way.
 * So, instead of manually coding clients for remote API and maybe using Springs RestTemplate
 * declare a client definition and the rest is generated during runtime for use
 */

@FeignClient(name = "exchangeRatesAPI", url = "${exchange.rates.api.url}")
public interface ExchangeRateClient {


    //https://api.exchangeratesapi.io/2020-03-05?base=GBP&symbols=USD
    @GetMapping(value = "/{date}?base={base}&symbols={target}")
    ResponseEntity<ExchangeRateAPIResponse> getExchangeRateForDate(
            @PathVariable String date,
            @PathVariable Currency base,
            @PathVariable Currency target
    );


    //https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-09-01&base=ILS&symbols=JPY
    @GetMapping(value = "/history?start_at={start_at}&end_at={end_at}&base={base}&symbols={target}")
    ResponseEntity<HistoryExchangeRateAPIResponse> getHistoricalExchangeRate(
            @PathVariable String start_at,
            @PathVariable String end_at,
            @PathVariable Currency base,
            @PathVariable Currency target
    );
}
