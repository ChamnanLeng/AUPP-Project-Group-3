package com.aupp.expensetracker.repository;

import com.aupp.expensetracker.Entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {
    List<CurrencyEntity> findByCurrencyName(String currencyName);
}
