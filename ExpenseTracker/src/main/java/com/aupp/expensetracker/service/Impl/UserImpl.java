package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.UserEntity;
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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public String registerUser(UserEntity userEntity) {
        UserEntity user = new UserEntity(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getEmail(),
                this.passwordEncoder.encode(userEntity.getPassword()),
                userEntity.getVerify()
        );
        UserEntity user1 = userRepository.findByEmail(userEntity.getEmail());
        if (user1 == null){
            userRepository.save(user);
            return user.getUserName();
        }else {
            return "User has been register!";
        }

    }

    UserEntity userEntity;

    @Override
    public LoginMesage loginUser(UserEntity userEntity) {
        String msg = "";
        UserEntity user1 = userRepository.findByEmail(userEntity.getEmail());
        if (user1 != null) {
            String password = userEntity.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<UserEntity> user = userRepository.findOneByEmailAndPassword(userEntity.getEmail(), encodedPassword);
                if (user.isPresent()) {
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
        return userRepository.findById(id).get();
    }
    @Override
    public void delete(UserEntity user) {
        userRepository.delete(user);
    }
    @Override
    public void createUser(UserEntity userEntity) {
        String textPassword = userEntity.getPassword();
        String encodePassword = passwordEncoder.encode(textPassword);
        userEntity.setPassword(encodePassword);
        userRepository.save(userEntity);
    }
    @Override
    public List< UserEntity > getAllUsersList() { return userRepository.findAll(); }
}

