package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.CurrencyEntity;
import com.aupp.expensetracker.Entity.ItemEntity;
import com.aupp.expensetracker.repository.CurrencyRepository;
import com.aupp.expensetracker.repository.ItemRepository;
import com.aupp.expensetracker.service.AdminPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPortalServiceImpl implements AdminPortalService {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Override
    public List<ItemEntity> getRecentItems(ItemEntity item, int limit) {
        return null;
    }

    @Override
    public void createItems(ItemEntity itemEntity) {
        itemRepository.save(itemEntity);
    }

    @Override
    public List<CurrencyEntity> getRecentCurrencies(CurrencyEntity currency, int limit) {
        return null;
    }

    @Override
    public void createCurrencies(CurrencyEntity currencyEntity) {
        currencyRepository.save(currencyEntity);
    }
}
