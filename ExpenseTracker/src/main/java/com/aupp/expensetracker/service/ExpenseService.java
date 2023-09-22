package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.Entity.UserEntity;

import java.util.List;

public interface ExpenseService {
    List<ExpenseEntity> getAllWithUserItemCurrency();
    List<ExpenseEntity> getRecentExpenses(UserEntity user, int limit);
    void createExpense(ExpenseEntity expenseEntity);
}
