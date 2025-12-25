package com.example.demo.service;

import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.Client;
import com.example.demo.entity.User;
import com.example.demo.repository.RoomDetailsRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Override
    public ResponseEntity<Object> validateLogin(User user) {
        HashMap<String,Object> outputObject = new HashMap<>();
        Optional<User> userOptional = userRepository.findByUserName(user.getUserName());
        if(userOptional.isPresent()){
            if(user.getPassword().equalsIgnoreCase(userOptional.get().getPassword())) {
                outputObject.put("login-status", "Success");
                outputObject.put("userObj", userOptional.get());
            }else{
                outputObject.put("login-status", "Failure");
                outputObject.put("errorMsg","User name and password is incorrect");
                return new ResponseEntity<>(outputObject,HttpStatus.NOT_FOUND);
            }
        }else{
            outputObject.put("login-status", "Failure");
            outputObject.put("errorMsg","User name is not found");
            return new ResponseEntity<>(outputObject,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(outputObject,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getRoomDetails(String startDate,String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
        Optional<List<RoomDetailsDTO>> getRoomDetails = roomDetailsRepository.getRoomDetailsList(startDateTime, endDateTime);
        return getRoomDetails.<ResponseEntity<Object>>map(roomDetailsDTOS -> new ResponseEntity<>(roomDetailsDTOS, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>("Please configure the room Details...", HttpStatus.OK));
    }

   
}


