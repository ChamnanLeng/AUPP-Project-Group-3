package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.repository.ExpenseRepository;
import com.aupp.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public List<ExpenseEntity> getAllWithUserItemCurrency() {
        return expenseRepository.findAllWithUserItemAndCurrency();
    }

    @Override
    public List<ExpenseEntity> getRecentExpenses(UserEntity user, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return expenseRepository.findTopNByUserOrderByExpTransactionIdDesc(user, pageable);
    }

    @Override
    public void createExpense(ExpenseEntity expenseEntity) {
        expenseRepository.save(expenseEntity);
    }
}
