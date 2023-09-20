package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.dto.LoginDTO;
import com.aupp.expensetracker.dto.UserDTO;
import com.aupp.expensetracker.repository.UserRepository;
import com.aupp.expensetracker.response.LoginMesage;
import com.aupp.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(UserDTO userDTO) {
        UserEntity user = new UserEntity(
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())
        );
        userRepo.save(user);
        return user.getUserName();
    }

    UserDTO userDTO;

    @Override
    public LoginMesage loginUser(LoginDTO loginDTO) {
        String msg = "";
        UserEntity user1 = userRepo.findByEmail(loginDTO.getEmail());
        if (user1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<UserEntity> employee = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginMesage("Login Success", true);
                } else {
                    return new LoginMesage("Login Failed", false);
                }
            } else {
                return new LoginMesage("password Not Match", false);
            }
        }else {
            return new LoginMesage("Email not exits", false);
        }
    }

    @Override
    public UserEntity findById(Integer id) {
        return userRepo.findById(id).get();
    }
    @Override
    public void delete(UserEntity user) {
        userRepo.delete(user);
    }
    @Override
    public void save(UserEntity user) {
        userRepo.save(user);
    }
    @Override
    public List< UserEntity > getAllUsersList() { return userRepo.findAll(); }
}

