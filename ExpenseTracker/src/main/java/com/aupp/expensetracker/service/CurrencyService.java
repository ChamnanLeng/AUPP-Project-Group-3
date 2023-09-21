package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.CurrencyEntity;

import java.util.List;

public interface CurrencyService {
    List<CurrencyEntity> getAllCurrencies();
}
