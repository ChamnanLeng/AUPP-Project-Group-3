package com.aupp.expensetracker.repository;

import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {
    @Query("SELECT ee FROM ExpenseEntity ee LEFT JOIN FETCH ee.userId LEFT JOIN FETCH ee.itemId LEFT JOIN FETCH ee.currencyId")
    List<ExpenseEntity> findAllWithUserItemAndCurrency();

    @Query("SELECT ee FROM ExpenseEntity ee WHERE ee.userId = :user ORDER BY ee.expTransactionId DESC")
    List<ExpenseEntity> findTopNByUserOrderByExpTransactionIdDesc(@Param("user") UserEntity user, Pageable pageable);

    List<ExpenseEntity> findByUserIdAndExpenseDateBetween(UserEntity userId, String fromDate, String toDate);
}
