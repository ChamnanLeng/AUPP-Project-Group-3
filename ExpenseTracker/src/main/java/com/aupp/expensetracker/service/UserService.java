package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.response.LoginMessage;
import com.aupp.expensetracker.response.RegisterResponse;

import java.util.List;


public interface UserService {
    LoginMessage loginUser(UserEntity userEntity);
    List < UserEntity > getAllUsersList();
    UserEntity findById(Integer id);
    UserEntity findByEmail(String email);
    void delete(UserEntity user);
    void saveUser(UserEntity user);
    String forgotPassword(String email);
    String resetPassword(String token, String password);
    RegisterResponse createUser(UserEntity user);
}