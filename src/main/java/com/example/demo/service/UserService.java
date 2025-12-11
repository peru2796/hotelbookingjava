package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User registerUser(String loginId, String rawPassword) {
        User user = new User();
        user.setUserName(loginId);
        return userRepository.save(user);
    }

    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByUserName(loginId);
    }
}
// Full implementation will include CRUD and JWT logic