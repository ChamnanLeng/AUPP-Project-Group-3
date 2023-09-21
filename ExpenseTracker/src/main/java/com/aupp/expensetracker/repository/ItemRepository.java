package com.aupp.expensetracker.repository;

import com.aupp.expensetracker.Entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
}
