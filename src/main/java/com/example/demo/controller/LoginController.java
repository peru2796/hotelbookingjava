package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import com.example.demo.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<Object> validateUserLogin(@RequestHeader("userName") String userName, @RequestHeader("password") String password){
        User user = User.builder().userName(userName).password(password).build();
        return loginService.validateLogin(user);
    }

    @GetMapping("/getRoomDetails")
    public ResponseEntity<Object> getRoomDetails(@RequestHeader(value = "date",required = false) String date){
        String startDate = LocalDate.now().toString()+" 00:00:00";
        String endDate = LocalDate.now().toString()+" 23:59:59";
        if(null != date){
           startDate = date+" 00:00:00";
            endDate = date+" 23:59:59";
       }


        return loginService.getRoomDetails(startDate,endDate);
    }

    @GetMapping("/getStatus")
    public ResponseEntity<Object> getStatus(){
        return null;
    }

}
