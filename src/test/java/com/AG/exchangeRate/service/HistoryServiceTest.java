package com.AG.exchangeRate.service;

import com.AG.exchangeRate.CreateExchangeRateEntitySample;
import com.AG.exchangeRate.CreateExchangeRateResponseDtoSample;
import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.entity.ExchangeRateEntity;
import com.AG.exchangeRate.model.mapper.ExchangeRateMapper;
import com.AG.exchangeRate.repository.HistoryRepository;
import com.AG.exchangeRate.service.impl.HistoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


/**
 * @author Rose
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class HistoryServiceTest {


    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private ExchangeRateMapper exchangeRateMapper;

    @InjectMocks
    private HistoryService historyService = new HistoryServiceImpl(historyRepository, exchangeRateMapper);

    @Test
    public void saveExchangeRateTest() throws Exception {

        ExchangeRateEntity exchangeRateEntity = CreateExchangeRateEntitySample.createEntitySample1();
        when(historyRepository.save(any(ExchangeRateEntity.class))).thenReturn(exchangeRateEntity);
        when(exchangeRateMapper.mapDtoToEntity(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity);

        ExchangeRateEntity exchangeRate = historyService.saveExchangeRate(CreateExchangeRateResponseDtoSample.createResponseSample1());

        assertThat(exchangeRate.getBase()).isSameAs(exchangeRateEntity.getBase());
    }

    @Test
    public void getDailyHistoryTest() throws Exception {

        ExchangeRateEntity exchangeRateEntity = CreateExchangeRateEntitySample.createEntitySample1();
        ExchangeRateResponseDto exchangeRateResponseDto = CreateExchangeRateResponseDtoSample.createResponseSample1();

        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Integer year = Integer.valueOf(dateString.substring(0, 4));
        Integer month = Integer.valueOf(dateString.substring(5, 7));
        Integer day = Integer.valueOf(dateString.substring(8, 10));
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);

        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        when(historyService.saveExchangeRate(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity);
        when(exchangeRateMapper.mapDtoToEntity(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity);
        when(historyRepository.findAllByCreationDate(date, pageable)).thenReturn(Arrays.asList(exchangeRateEntity));
        when(exchangeRateMapper.mapEntityToDto(any(ExchangeRateEntity.class))).thenReturn(exchangeRateResponseDto);
        List<ExchangeRateResponseDto> exchangeRateResponseDtos = historyService.getDailyHistory(year, month, day, pageable);
        assertThat(exchangeRateResponseDtos.get(0).getBase()).isSameAs(exchangeRateEntity.getBase());
    }

    @Test
    public void getMonthlyHistoryTest() throws Exception {

        ExchangeRateEntity exchangeRateEntity1 = CreateExchangeRateEntitySample.createEntitySample1();
        ExchangeRateEntity exchangeRateEntity2 = CreateExchangeRateEntitySample.createEntitySample2();
        ExchangeRateResponseDto exchangeRateResponseDto1 = CreateExchangeRateResponseDtoSample.createResponseSample1();
        ExchangeRateResponseDto exchangeRateResponseDto2 = CreateExchangeRateResponseDtoSample.createResponseSample2();

        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Integer year = Integer.valueOf(dateString.substring(0, 4));
        Integer month = Integer.valueOf(dateString.substring(5, 7));

        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        when(historyService.saveExchangeRate(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity1);
        when(exchangeRateMapper.mapDtoToEntity(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity1);

        when(historyService.saveExchangeRate(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity2);
        when(exchangeRateMapper.mapDtoToEntity(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity2);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate initial = LocalDate.of(year, month, 01);

        Date dateStart = Date.from(initial.withDayOfMonth(1).atStartOfDay(defaultZoneId).toInstant());
        Date dateEnd = Date.from(initial.withDayOfMonth(initial.lengthOfMonth()).atStartOfDay(defaultZoneId).toInstant());


        when(historyRepository.findAllByCreationDateBetween(dateStart, dateEnd, pageable)).thenReturn(Arrays.asList(exchangeRateEntity1, exchangeRateEntity2));
        when(exchangeRateMapper.mapEntityToDtoList(anyList())).thenReturn(Arrays.asList(exchangeRateResponseDto1, exchangeRateResponseDto2));
        List<ExchangeRateResponseDto> exchangeRateResponseDtos = historyService.getMonthlyHistory(year, month, pageable);

        assertThat(exchangeRateResponseDtos.get(0).getBase()).isSameAs(exchangeRateResponseDto1.getBase());
    }

}
