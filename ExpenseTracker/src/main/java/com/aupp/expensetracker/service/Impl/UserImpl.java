package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.repository.UserRepository;
import com.aupp.expensetracker.response.LoginMessage;
import com.aupp.expensetracker.response.RegisterResponse;
import com.aupp.expensetracker.service.UserService;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserImpl implements UserService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

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
                userEntity.getToken(),
                userEntity.getTokenCreationDate()
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
    public LoginMessage loginUser(UserEntity userEntity) {
        String msg = "";
        UserEntity user1 = userRepository.findByEmail(userEntity.getEmail());
        if (user1 != null) {
            String password = userEntity.getPassword();
            String encodedPassword = user1.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<UserEntity> user = userRepository.findOneByEmailAndPassword(userEntity.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginMessage(user1.getUserId(), "Login Success", true);
                } else {
                    return new LoginMessage(user1.getUserId(),"Login Failed", false);
                }
            } else {
                return new LoginMessage(user1.getUserId(),"password Not Match", false);
            }
        }else {
            return new LoginMessage(0,"Email not exits", false);
        }
    }

    @Override
    public UserEntity findById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(userEntity);
    }

    @Override
    public RegisterResponse createUser(UserEntity userEntity) {
        if (isEmailAlreadyInUse(userEntity.getEmail())){
            return new RegisterResponse("Email is already in use.", false);
        }
        String textPassword = userEntity.getPassword();
        String encodePassword = passwordEncoder.encode(textPassword);
        userEntity.setPassword(encodePassword);
        userRepository.save(userEntity);
        return new RegisterResponse("Success Register", true);
    }
    @Override
    public List< UserEntity > getAllUsersList() { return userRepository.findAll(); }

    @Override
    public String forgotPassword(String email) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        UserEntity user = userOptional.get();
        user.setToken(generateToken());

        user.setTokenCreationDate(LocalDateTime.now());

        user = userRepository.save(user);

        return user.getToken();
    }

    @Override
    public String resetPassword(String token, String password) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByToken(token));
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

        userRepository.save(user);

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

    private boolean isEmailAlreadyInUse(String email){
        UserEntity existingUser = userRepository.findByEmail(email);
        return existingUser != null;
    }
}

