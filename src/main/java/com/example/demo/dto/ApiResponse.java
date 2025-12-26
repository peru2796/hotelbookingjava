package com.example.demo.dto;

import lombok.Data;

@Data
public class ApiResponse {
    private String status;
    private String message;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // getters & setters
}
