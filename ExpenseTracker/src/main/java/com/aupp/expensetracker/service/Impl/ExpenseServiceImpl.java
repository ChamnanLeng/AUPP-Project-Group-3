package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.CurrencyEntity;
import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.Entity.ItemEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.repository.ExpenseRepository;
import com.aupp.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public LocalDate findDateWithMostExpenses(UserEntity userId, String fromDate, String toDate) {
        List<ExpenseEntity> expenseEntities = expenseRepository.findByUserIdAndExpenseDateBetween(userId, fromDate, toDate);

        Map<LocalDate, Double> totalExpensesByDate = expenseEntities.stream()
                .collect(Collectors.groupingBy(
                        expense -> LocalDate.parse(expense.getExpenseDate()),
                        Collectors.summingDouble(expense -> {
                            double expenseAmount = expense.getExpenseAmount();
                            CurrencyEntity currency = expense.getCurrencyId();

                            // Calculate the amount in USD using both currencyValue and currencyRateValue
                            return expenseAmount * ((double) currency.getCurrencyValue() / currency.getCurrencyRateValue());
                        })
                ));

        // Find the date with the highest total expenses
        LocalDate dateWithMostExpenses = null;
        double highestTotalExpenses = 0.0;

        for (Map.Entry<LocalDate, Double> entry : totalExpensesByDate.entrySet()) {
            double totalExpenses = entry.getValue();

            if (totalExpenses > highestTotalExpenses) {
                highestTotalExpenses = totalExpenses;
                dateWithMostExpenses = entry.getKey();
            }
        }

        return dateWithMostExpenses;
    }

    @Override
    public List<ItemEntity> findTop3ItemsByDateRange(UserEntity userId, String fromDate, String toDate) {
        List<ExpenseEntity> expenseEntities = expenseRepository.findByUserIdAndExpenseDateBetween(userId, fromDate, toDate);

        Map<ItemEntity, Double> itemTotalExpenses = new HashMap<>();

        for (ExpenseEntity expense : expenseEntities) {
            ItemEntity item = expense.getItemId();
            double expenseAmount = expense.getExpenseAmount();

            // Convert expenseAmount to USD equivalent
            double usdEquivalent = calculateUsdEquivalent(expenseAmount, expense.getCurrencyId());

            // Update the total expense for the item
            itemTotalExpenses.put(item, itemTotalExpenses.getOrDefault(item, 0.0) + usdEquivalent);
        }
        // Sort the items by total expenses in descending order
        List<ItemEntity> topItems = itemTotalExpenses.entrySet()
                .stream()
                .sorted(Map.Entry.<ItemEntity, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topItems;
    }

    @Override
    public double calculateAverageSpendingByDateRange(UserEntity userId, String fromDate, String toDate) {
        List<ExpenseEntity> expenseEntities = expenseRepository.findByUserIdAndExpenseDateBetween(userId, fromDate, toDate);
        double totalExpenseInUSD = expenseEntities.stream()
                .mapToDouble(expense -> {
                    // Calculate the expense in USD based on currency rate
                    double currencyRate = expense.getCurrencyId().getCurrencyRateValue();
                    return expense.getExpenseAmount() / currencyRate;
                })
                .sum();

        // Calculate the average spending
        long numberOfExpenses = expenseEntities.size();
        if (numberOfExpenses == 0) {
            return 0.0; // Avoid division by zero
        } else {
            return totalExpenseInUSD / numberOfExpenses;
        }
    }

    private double calculateUsdEquivalent(double expenseAmount, CurrencyEntity currency) {
        // Calculate USD equivalent based on currency rate
        double rate = currency.getCurrencyRateValue();
        return expenseAmount / rate;
    }
}
