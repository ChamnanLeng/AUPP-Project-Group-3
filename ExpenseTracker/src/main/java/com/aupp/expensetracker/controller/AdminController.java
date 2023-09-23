package com.aupp.expensetracker.controller;

import com.aupp.expensetracker.Entity.AdminEntity;
import com.aupp.expensetracker.response.LoginMessage;
import com.aupp.expensetracker.response.RegisterResponse;
import com.aupp.expensetracker.service.Impl.AdminImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminImpl adminService;

    @GetMapping("/registration")
    public String showRegisterForm(Model model){
        model.addAttribute("registration", new AdminEntity());
        return "register_admin";
    }

    @PostMapping("/create")
    public String createAdmin(@ModelAttribute("registration") @Validated AdminEntity adminEntity) {
        RegisterResponse registerResponse = adminService.createAdmin(adminEntity);
        if (registerResponse.getStatus().equals(true)){
            return "redirect:/login_admin";
        }
        else {
            return "register_admin";
        }
    }
    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("login_admin", new AdminEntity());
        return "login_admin";
    }

    @PostMapping("/login_page")
    public String loginAdmin(@ModelAttribute("login_user") @Validated AdminEntity adminEntity, HttpSession session) {
        LoginMessage loginResponse = adminService.loginAdmin(adminEntity);
        if (loginResponse.getStatus().equals(true)){
            session.setAttribute("adminId", loginResponse.getUserId()); //admin, just named user
            return "redirect:/portal";
        }else {
            return "login_admin";
        }
    }
}
