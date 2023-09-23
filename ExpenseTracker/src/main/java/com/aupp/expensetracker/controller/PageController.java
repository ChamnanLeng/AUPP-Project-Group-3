package com.aupp.expensetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register")
    public String registerPage() {
        return "register";
    }

    @RequestMapping("/login_admin")
    public String adminLoginPage(){return "login_admin";}

    @RequestMapping("/register_admin")
    public String adminRegisterPage() {
        return "register_admin";
    }
    
}

