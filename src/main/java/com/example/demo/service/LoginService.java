package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    ResponseEntity<Object> validateLogin(User user);
    ResponseEntity<Object> getRoomDetails();
}
