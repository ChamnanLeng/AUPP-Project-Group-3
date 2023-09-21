package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.CurrencyEntity;
import com.aupp.expensetracker.repository.CurrencyRepository;
import com.aupp.expensetracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Override
    public List<CurrencyEntity> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
