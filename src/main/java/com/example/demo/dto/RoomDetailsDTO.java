package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDetailsDTO {

    private Integer floorNumber;
    private Integer roomNumber;
    private String floorName;
    private String roomName;
    private Long id;

    RoomDetailsDTO(Integer roomNumber,String roomName,Integer floorNumber,String floorName,Long id){
        this.floorNumber = floorNumber;
        this.floorName = floorName;
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.id = id;
    }
}
