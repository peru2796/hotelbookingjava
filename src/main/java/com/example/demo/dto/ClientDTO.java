package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClientDTO {

    private String firstName;
    private String lastName;
    private String initial;
    private String email;

    private Long id;

    private String mobileNumber;


    private String idNumber;

    private String Address1;
    private String Address2;

    private String city;

    private String state;

    private Integer pinCode;

    private byte[] image;

    private String fileName;

    private String fileType;

    ClientDTO(String firstName, String lastName,String initial, String email, Long id, String mobileNumber, String idNumber,String Address1,String Address2,String city,String state,Integer pinCode
    ,byte[] image,String fileName,String fileType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.initial = initial;
        this.email = email;
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.idNumber = idNumber;
        this.Address1 = Address1;
        this.Address2 = Address2;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.image = image;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
