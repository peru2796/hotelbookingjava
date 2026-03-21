package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class BarChartResponse {
    private String date;
    private Long count;

    public BarChartResponse(LocalDate date, Long count) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.date  = date.format(fmt);   // ✅ Format here
        this.count = count;
    }

    // ✅ Accept Object — handles any type JPQL returns
    public BarChartResponse(Object date, Long count) {
        this.date  = date != null ? date.toString() : "";
        this.count = count;
    }


    public Object getDate() { return date; }

    public Long getCount() { return count; }

    // getters & setters
}
