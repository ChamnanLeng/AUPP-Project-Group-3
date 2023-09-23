package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.Entity.AdminEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.repository.AdminRepository;
import com.aupp.expensetracker.response.LoginMessage;
import com.aupp.expensetracker.response.RegisterResponse;
import com.aupp.expensetracker.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    AdminEntity adminEntity;
    @Autowired
    public AdminImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder){
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public RegisterResponse createAdmin(AdminEntity adminEntity) {
        String textPassword = adminEntity.getAdminPassword();
        String encodePassword = passwordEncoder.encode(textPassword);
        adminEntity.setAdminPassword(encodePassword);
        if (isEmailAlreadyInUse(adminEntity.getAdminEmail())){
            return new RegisterResponse("Email is already in use.", false);
        }else {
            adminRepository.save(adminEntity);
            return new RegisterResponse("Success Register", true);
        }
    }

    @Override
    public AdminEntity findByEmail(String email) {
        return adminRepository.findByAdminEmail(email);
    }

    @Override
    public LoginMessage loginAdmin(AdminEntity adminEntity) {
        AdminEntity admin = adminRepository.findByAdminEmail(adminEntity.getAdminEmail());
        if (admin != null) {
            String password = adminEntity.getAdminPassword();
            String encodedPassword = admin.getAdminPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<AdminEntity> user = adminRepository.findOneByAdminEmailAndAdminPassword(adminEntity.getAdminEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginMessage(admin.getAdminId(), "Login Success", true);
                } else {
                    return new LoginMessage(admin.getAdminId(),"Login Failed", false);
                }
            } else {
                return new LoginMessage(admin.getAdminId(),"password Not Match", false);
            }
        }else {
            return new LoginMessage(0,"Email not exits", false);
        }
    }

    @Override
    public AdminEntity findById(Integer id) {
        return adminRepository.findById(id).get();
    }

    private boolean isEmailAlreadyInUse(String email){
        AdminEntity existingAdmin = adminRepository.findByAdminEmail(email);
        return existingAdmin != null;
    }
}
