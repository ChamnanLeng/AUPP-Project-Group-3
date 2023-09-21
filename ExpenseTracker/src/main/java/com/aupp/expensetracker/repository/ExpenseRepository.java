package com.aupp.expensetracker.repository;

import com.aupp.expensetracker.Entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {
    @Query("SELECT ee FROM ExpenseEntity ee LEFT JOIN FETCH ee.userId LEFT JOIN FETCH ee.itemId LEFT JOIN FETCH ee.currencyId")
    List<ExpenseEntity> findAllWithUserItemAndCurrency();

    List<ExpenseEntity> findTop10ByOrderByExpTransactionIdDesc();
}
