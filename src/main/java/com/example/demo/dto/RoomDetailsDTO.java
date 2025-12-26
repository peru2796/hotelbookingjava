package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDetailsDTO {

    private Integer floorNumber;
    private Integer roomNumber;
    private String floorName;
    private String roomName;
    private Long id;
    private String roomType;
    private double amount;


    RoomDetailsDTO(Integer roomNumber,String roomName,Integer floorNumber,String floorName,Long id,String roomType,double amount){
        this.floorNumber = floorNumber;
        this.floorName = floorName;
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.id = id;
        this.amount = amount;
        this.roomType = roomType;
    }
}
