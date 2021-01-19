package com.AG.exchangeRate.controller;

import com.AG.exchangeRate.model.constant.Constants;
import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.enums.Currency;
import com.AG.exchangeRate.model.errors.ErrorCreation;
import com.AG.exchangeRate.service.ExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @author Rose
 */
@Api(value = "Swagger2DemoRestController", description = "REST APIs related to get and calculate exchange rate and communicate with the API of https://exchangeratesapi.io/ ")
@Slf4j
@RestController
@RequestMapping("/api/exchange-rate")
@AllArgsConstructor
public class ExchangeRateController {


    ExchangeRateService exchangeRateService;

    /**
     * This method is used to communicate with the API of https://exchangeratesapi.io/ for getting exchange rate
     * and calculate average of the five days before the requested date
     * and determine the exchange rate trend
     * @param date specifies the requested date for exchange rate
     * @param baseCurrency
     * @param targetCurrency
     * @return ExchangeRateResponseDto
     */
    @ApiOperation(value = " This method is used to communicate with the API of https://exchangeratesapi.io/ for " +
            "getting exchange rate and calculate average of the five days before the requested date and determine the exchange rate trend",
            response = ExchangeRateResponseDto.class, tags = "ExchangeRateForGivenDateAndCurrencies")
    @GetMapping("/{date}/{baseCurrency}/{targetCurrency}")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeRateResponseDto getExchangeRateForGivenDateAndCurrencies(@PathVariable final String date, @PathVariable final Currency baseCurrency, @PathVariable final Currency targetCurrency) {
        log.info("Validating date " + date);
        LocalDate localDate = isValidDate(date);
        if (!isValidDateFormat(date) || localDate == null) {
            throw ErrorCreation.INVALID_DATE_INPUT.ErrorCreation("Date must have yyyy-mm-dd format");
        }
        LocalDate minimum = LocalDate.parse(Constants.MINIMUM_DATE);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        if (!isValidDateRange(localDate, minimum, yesterday)) {
            throw ErrorCreation.INVALID_DATE_INPUT.ErrorCreation("Date must be between " + minimum.toString() + " and yesterday");
        }

        log.info("Calculating exchange rate, average and trend for params: Date: {}, Base currency : {}, Target currency: {}", date, baseCurrency, targetCurrency);
        return exchangeRateService.getExchangeRateForGivenDateAndCurrencies(date, baseCurrency, targetCurrency);
    }

    /**
     * Validates the date format.
     *
     * @param date the input string with the date
     * @return true if date has correct format
     */
    public final boolean isValidDateFormat(String date) {
        if (!date.matches(Constants.ISO_DATE_FORMAT)) {
            return false;
        }
        return true;
    }

    /**
     * Validates the date.
     *
     * @param date the input string with the date
     * @return true if date is correct
     */
    public final LocalDate isValidDate(String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            return null;
        }
        return localDate;
    }

    /**
     * Validates if an input date is between min and max dates.
     *
     * @param date the input date
     * @param min  the minimum date
     * @param max  the maximum date
     * @return true if the input date is between min and max
     */
    public final boolean isValidDateRange(LocalDate date, LocalDate min, LocalDate max) {
        if (date.isBefore(min) || date.isAfter(max)) {
            return false;
        }
        return true;
    }


}
