package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import com.example.demo.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<Object> validateUserLogin(@RequestHeader("userName") String userName, @RequestHeader("password") String password){
        User user = User.builder().userName(userName).password(password).build();
        return loginService.validateLogin(user);
    }

    @GetMapping("/getRoomDetails")
    public ResponseEntity<Object> getRoomDetails(){
        return loginService.getRoomDetails();
    }
}