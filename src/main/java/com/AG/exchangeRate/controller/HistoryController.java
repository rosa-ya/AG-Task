package com.AG.exchangeRate.controller;

import com.AG.exchangeRate.model.constant.Constants;
import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.errors.ErrorCreation;
import com.AG.exchangeRate.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rose
 */
@Api(value = "Swagger2DemoRestController", description = "REST APIs related to get exchange rate history from DB")
@Slf4j
@RestController
@RequestMapping("/api/exchange-rate/history")
@AllArgsConstructor
public class HistoryController {


    HistoryService historyService;


    /**
     * This method is used to get Daily history from DB
     *
     * @param yyyy specifies the year of requested date
     * @param MM   specifies the month of requested date
     * @param dd   specifies the day of requested date
     * @return List of ExchangeRateResponseDto
     */
    @ApiOperation(value = "Get exchange rate daily history", response = ExchangeRateResponseDto.class, tags = "Daily History")
    @GetMapping("/daily/{yyyy}/{MM}/{dd}")
    public @ResponseBody
    List<ExchangeRateResponseDto> getDailyHistory(@PathVariable Integer yyyy, @PathVariable Integer MM, @PathVariable Integer dd, Pageable pageable) {
        log.info("Validation year " + yyyy);
        if (!isValidYearFormat(yyyy) || !isValidMonthDayFormat(MM) || !isValidMonthDayFormat(dd)) {
            throw ErrorCreation.INVALID_INPUT.ErrorCreation("");
        }
        log.info("Get historical daily information for params  (yyyy/MM/dd): {}/{}/{}", yyyy, MM, dd);
        return historyService.getDailyHistory(yyyy, MM, dd, pageable);
    }

    /**
     * This method is used to get Monthly history from DB
     *
     * @param yyyy specifies the year of requested date
     * @param MM   specifies the month of requested date
     * @return List of ExchangeRateResponseDto
     */
    @ApiOperation(value = "Get exchange rate  monthly history", response = ExchangeRateResponseDto.class, tags = "Monthly History")
    @GetMapping("/monthly/{yyyy}/{MM}")
    public List<ExchangeRateResponseDto> getMonthlyHistory(@PathVariable Integer yyyy, @PathVariable Integer MM, Pageable pageable) {
        log.info("Get historical monthly information for params  (yyyy/MM): {}/{}", yyyy, MM);
        return historyService.getMonthlyHistory(yyyy, MM, pageable);
    }

    /**
     * Validates the year format.
     *
     * @param year the input Integer with the year
     * @return true if year has correct format
     */
    public final boolean isValidYearFormat(Integer year) {
        if (!year.toString().matches(Constants.YEAR_FORMAT)) {
            return false;
        }
        return true;
    }

    /**
     * Validates the month/day format.
     *
     * @param monthDay the input Integer with the year
     * @return true if month/day has correct format
     */
    public final boolean isValidMonthDayFormat(Integer monthDay) {
        if (!monthDay.toString().matches(Constants.MONTH_DAY_FORMAT)) {
            return false;
        }
        return true;
    }
}
