package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.CurrencyEntity;
import com.aupp.expensetracker.Entity.ItemEntity;

import java.util.List;

public interface AdminPortalService {
    List<ItemEntity> getRecentItems(ItemEntity item, int limit);
    void createItems(ItemEntity itemEntity);
    List<CurrencyEntity> getRecentCurrencies(CurrencyEntity currency, int limit);
    void createCurrencies(CurrencyEntity currencyEntity);
}
