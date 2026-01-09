package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BarChartResponse {
    private Object day;
    private Long cnt;

    public BarChartResponse(Object day, Long cnt) {
        this.day = day;
        this.cnt = cnt;
    }


    public Object getDay() { return day; }
    public void setDay(Object day) { this.day = day; }
    public Long getCnt() { return cnt; }
    public void setCnt(Long cnt) { this.cnt = cnt; }

    // getters & setters
}
