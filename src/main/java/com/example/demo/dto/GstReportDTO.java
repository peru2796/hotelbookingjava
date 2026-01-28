package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GstReportDTO {

   List<BookingDTO> bookingDTOList;

   private Double grantTotal;
   private Double baseFareTotal;
   private Double sgstTotal;
   private Double cgstTotal;
}
