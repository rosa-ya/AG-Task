package com.AG.exchangeRate.repository;

import com.AG.exchangeRate.model.entity.ExchangeRateEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Rose
 */
@Repository
public interface HistoryRepository extends JpaRepository<ExchangeRateEntity, Long> , PagingAndSortingRepository<ExchangeRateEntity, Long> {

    List<ExchangeRateEntity> findAllByCreationDate(Date creationDate, Pageable pageable);

    List<ExchangeRateEntity> findAllByCreationDateBetween(Date dateFrom, Date dateUntil,Pageable pageable);
}
