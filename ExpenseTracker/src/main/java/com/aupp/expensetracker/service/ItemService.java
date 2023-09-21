package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.ItemEntity;

import java.util.List;

public interface ItemService {
    List<ItemEntity> getAllItems();
}
