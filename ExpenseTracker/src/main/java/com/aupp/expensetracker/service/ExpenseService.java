package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.Entity.ItemEntity;
import com.aupp.expensetracker.Entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    List<ExpenseEntity> getAllWithUserItemCurrency();
    List<ExpenseEntity> getRecentExpenses(UserEntity user, int limit);
    void createExpense(ExpenseEntity expenseEntity);
    LocalDate findDateWithMostExpenses(UserEntity userId, String fromDate, String toDate);
    List<ItemEntity> findTop3ItemsByDateRange(UserEntity userId, String fromDate, String toDate);
    double calculateAverageSpendingByDateRange(UserEntity userId, String fromDate, String toDate);
}
