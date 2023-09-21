package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.response.LoginMesage;

import java.util.List;


public interface UserService {
    String registerUser(UserEntity userEntity);
    LoginMesage loginUser(UserEntity userEntity);
    List < UserEntity > getAllUsersList();
    UserEntity findById(Integer id);
    void delete(UserEntity user);
    void save(UserEntity user);
}