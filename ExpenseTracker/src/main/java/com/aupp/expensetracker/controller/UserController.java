package com.aupp.expensetracker.controller;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.response.LoginMesage;
import com.aupp.expensetracker.service.EmailService;
import com.aupp.expensetracker.service.Impl.UserImpl;
import com.aupp.expensetracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserImpl userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        Map<String, Object> jsonResponseMap = new LinkedHashMap<>();
        List<UserEntity> listOfUsers = userService.getAllUsersList();
        List<UserEntity> listOfUserDto = new ArrayList<>();

        if (!listOfUsers.isEmpty()) {
            for (UserEntity user : listOfUsers) {
                listOfUserDto.add(modelMapper.map(user, UserEntity.class));
            }
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("data", listOfUserDto);
            jsonResponseMap.put("count",listOfUserDto.size());
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.OK);
        } else {
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/registration")
    public String showRegisterForm(Model model){
        model.addAttribute("registration", new UserEntity());
        return "register";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("registration") @Validated UserEntity userEntity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "register";
        }
        userService.createUser(userEntity);
        return "redirect:/expenses/page";
    }

    @GetMapping("/login_user")
    public String showLoginForm(Model model){
        model.addAttribute("login_user", new UserEntity());
        return "login";
    }

    @PostMapping("/login_page")
    public String loginUser(@ModelAttribute("login_user") @Validated UserEntity userEntity) {
        LoginMesage loginResponse = userService.loginUser(userEntity);
        if (loginResponse.getStatus().equals(true)){
            return "redirect:/expenses";
        }else {
            return "login";
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        Map<String, Object> jsonResponseMap = new LinkedHashMap<>();
        try {
            UserEntity user = userService.findById(id);
            UserEntity userEntity = modelMapper.map(user, UserEntity.class);
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("data", userEntity);
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.OK);
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        Map<String, Object> jsonResponseMap = new LinkedHashMap<>();
        try {
            UserEntity user = userService.findById(id);
            userService.delete(user);
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("message", "Record is deleted successfully!");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.OK);
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity userEntity) {
        Map<String, Object> jsonResponseMap = new LinkedHashMap<>();
        try {
            UserEntity user = userService.findById(id);
            if (user == null) {
                jsonResponseMap.put("status", 0);
                jsonResponseMap.put("message", "User not found");
                return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
            }

            if (!userEntity.getUserName().isEmpty()) {
                user.setUserName(userEntity.getUserName());
            }

            if (!userEntity.getEmail().isEmpty()) {
                user.setEmail(userEntity.getEmail());
            }

            if (!userEntity.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
                user.setPassword(encodedPassword);
            }

            userService.createUser(user);
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("data", userService.findById(id));
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.OK);
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/forgot-password")
//    public String forgotPassword(@RequestParam String email) {
//
//        String response = userService.forgotPassword(email);
//
////        if (!response.startsWith("Invalid")) {
////            response = "http://localhost:8080/user/reset-password?token=" + response;
////        }
//
//        return response;
//    }

    @PatchMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        String token = userService.forgotPassword(email);
        try{
            UserEntity user = userService.findByEmail(email);
            if (user != null) {
                user.setToken(token);
                user.setEmail(email);
                userService.saveUser(user);
                String resetPasswordLink ="<p>Hello,</p>"
                        + "<p>You have requested to reset your password.</p>"
                        + "<p>Here is your verify </p>"
                        + "<h2 style='color:#069A8E;text-align:center;'>" +
                        token+ "</h2>"
                        + "<br>"
                        + "<p style='color:red;'>Ignore this email if you do remember your password, "
                        + "or you have not made the request.</p>"  ;
                System.out.println("Here is the reset passwrod likm"+ resetPasswordLink);
                emailService.sendByMail(email,resetPasswordLink);


            }




        }catch (Exception e)
        {
//            return new ResponseEntity<>(new ErrorResponse(406, CustomMessage.USER_NOT_FOUND, requestTime, errors), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
        }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password) {

        String encodedPassword = passwordEncoder.encode(password);

        return userService.resetPassword(token, encodedPassword);
    }
}