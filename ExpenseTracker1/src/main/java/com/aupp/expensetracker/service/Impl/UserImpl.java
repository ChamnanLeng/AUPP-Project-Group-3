package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.repository.UserRepository;
import com.aupp.expensetracker.response.LoginMesage;
import com.aupp.expensetracker.service.UserService;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserImpl implements UserService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(UserEntity userEntity) {
        UserEntity user = new UserEntity(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getEmail(),
                this.passwordEncoder.encode(userEntity.getPassword()),
                userEntity.getToken(),
                userEntity.getTokenCreationDate()
        );
        UserEntity user1 = userRepo.findByEmail(userEntity.getEmail());
        if (user1 == null){
            userRepo.save(user);
            return user.getUserName();
        }else {
            return "User has been register!";
        }

    }

    UserEntity userEntity;

    @Override
    public LoginMesage loginUser(UserEntity userEntity) {
        String msg = "";
        UserEntity user1 = userRepo.findByEmail(userEntity.getEmail());
        if (user1 != null) {
            String password = userEntity.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<UserEntity> user = userRepo.findOneByEmailAndPassword(userEntity.getEmail(), encodedPassword);
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
        return userRepo.findById(id).get();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email);
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

    @Override
    public String forgotPassword(String email) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepo.findByEmail(email));
        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        UserEntity user = userOptional.get();
        user.setToken(generateToken());

        user.setTokenCreationDate(LocalDateTime.now());

        user = userRepo.save(user);

        return user.getToken();
    }

    @Override
    public String resetPassword(String token, String password) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepo.findByToken(token));
        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }

        UserEntity user = userOptional.get();

        user.setPassword(password);
        user.setToken(null);
        user.setTokenCreationDate(null);

        userRepo.save(user);

        return "Your password has been successfully updated.";
    }

    private String generateToken() {
//        StringBuilder token = new StringBuilder();
//        return token.append(UUID.randomUUID().toString())
//                .append(UUID.randomUUID().toString()).toString();
        return RandomString.make(6);
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}

