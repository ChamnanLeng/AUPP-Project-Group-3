package com.aupp.expensetracker.service;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.dto.LoginDTO;
import com.aupp.expensetracker.dto.UserDTO;
import com.aupp.expensetracker.response.LoginMesage;

import java.util.List;


public interface UserService {
    String registerUser(UserDTO userDTO);
    LoginMesage loginUser(LoginDTO loginDTO);
    List < UserEntity > getAllUsersList();
    UserEntity findById(Integer id);
    void delete(UserEntity user);
    void save(UserEntity user);
}