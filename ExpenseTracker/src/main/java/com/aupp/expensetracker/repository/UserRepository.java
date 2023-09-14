package com.aupp.expensetracker.repository;

import com.aupp.expensetracker.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity,Integer> {
}
