package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.AdminEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.response.LoginMessage;
import com.aupp.expensetracker.response.RegisterResponse;

public interface AdminService {
    RegisterResponse createAdmin(AdminEntity adminEntity);
    AdminEntity findByEmail(String email);
    LoginMessage loginAdmin(AdminEntity userEntity);
    AdminEntity findById(Integer id);
}
