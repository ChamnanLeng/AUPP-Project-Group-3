package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.ExpenseEntity;

import java.util.List;

public interface ExpenseService {
    List<ExpenseEntity> getAllWithUserItemCurrency();
    List<ExpenseEntity> getRecentExpenses(int limit);
    void createExpense(ExpenseEntity expenseEntity);
}
