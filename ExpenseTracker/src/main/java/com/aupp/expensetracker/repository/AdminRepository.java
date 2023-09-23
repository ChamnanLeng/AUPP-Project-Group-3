package com.aupp.expensetracker.repository;

import com.aupp.expensetracker.Entity.AdminEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    AdminEntity findByAdminEmail(String adminEmail);
    Optional<AdminEntity> findOneByAdminEmailAndAdminPassword(String email, String password);
}
