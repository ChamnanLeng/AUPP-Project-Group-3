package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.ItemEntity;
import com.aupp.expensetracker.repository.ItemRepository;
import com.aupp.expensetracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Override
    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }
}
