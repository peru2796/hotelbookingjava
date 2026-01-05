package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DashboardDTO {

    private Long noOfAvailableRooms;
    private Long noOfOccupiedRooms;
    private Integer revenueEarnedToday;
    private Long noOfCheckins;

    private Long noOfCheckouts;

}
