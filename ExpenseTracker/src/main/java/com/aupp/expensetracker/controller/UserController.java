package com.aupp.expensetracker.controller;

import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.response.LoginMesage;
import com.aupp.expensetracker.service.Impl.UserImpl;
import com.aupp.expensetracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/v1/user")
public class UserController {
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

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("registerUser", new UserEntity());
        return "registerPage";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("registerUser") @Validated UserEntity userEntity, BindingResult bindingResult, Model model) {
        //String id = userService.registerUser(userEntity);
        if (bindingResult.hasErrors()){
            //model.addAttribute("registerUser", new UserEntity());
            return "registerPage";
        }
        userService.createUser(userEntity);
        return "redirect:/expenses/page";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity userEntity) {
        LoginMesage loginResponse = userService.loginUser(userEntity);
        return ResponseEntity.ok(loginResponse);
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
}