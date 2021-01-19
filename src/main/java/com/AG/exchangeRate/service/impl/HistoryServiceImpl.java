package com.AG.exchangeRate.service.impl;

import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.entity.ExchangeRateEntity;
import com.AG.exchangeRate.model.mapper.ExchangeRateMapper;
import com.AG.exchangeRate.repository.HistoryRepository;
import com.AG.exchangeRate.service.HistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rose
 */
@Slf4j
@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {


    private HistoryRepository historyRepository;

    private ExchangeRateMapper exchangeRateMapper;

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    @Override
    @Transactional
    public ExchangeRateEntity saveExchangeRate(ExchangeRateResponseDto exchangeRateResponseDto) {
        ExchangeRateEntity exchangeRateEntity = historyRepository.save(exchangeRateMapper.mapDtoToEntity(exchangeRateResponseDto));
        log.info("ExchangeRateEntity: {} saved", exchangeRateEntity);
        return exchangeRateEntity;
    }

    @Override
    @Transactional
    public List<ExchangeRateResponseDto> getDailyHistory(Integer year, Integer month, Integer day, Pageable pageable) {
        Date date = parseDate(year + "-" + month + "-" + day);
        List<ExchangeRateEntity> exchangeRateEntities = historyRepository.findAllByCreationDate(date, pageable);

        log.info("Daily history for date: {}/{}/{} is {} ", year, month, date, exchangeRateEntities);

        return exchangeRateEntities.stream().map(exchangeRateMapper::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ExchangeRateResponseDto> getMonthlyHistory(Integer year, Integer month, Pageable pageable) {


        LocalDate initial = LocalDate.of(year, month, 01);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date dateStart = Date.from(initial.withDayOfMonth(1).atStartOfDay(defaultZoneId).toInstant());
        Date dateEnd = Date.from(initial.withDayOfMonth(initial.lengthOfMonth()).atStartOfDay(defaultZoneId).toInstant());

        List<ExchangeRateEntity> exchangeRateEntities = historyRepository.findAllByCreationDateBetween(dateStart, dateEnd, pageable);

        log.info("Monthly history for: {}/{}", year, month);
        return exchangeRateMapper.mapEntityToDtoList(exchangeRateEntities);
    }


}
