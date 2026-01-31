package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDTO {

    private Long bookingId;
    private Long roomId;
    private Long roomNumber;
    private Long clientId;
    private Double totalAmount;
    private Double amountPaid;
    private Double amountRemaining;
    private String paymentType;
    private Integer transactionStatus;
    private Double miscellaneousCharge;

    private String gstInNo;
    private Long billingId;
    private String billingNumber;

    private Double baseFare;
    private Double sgst;
    private Double cgst;

    private LocalDateTime  bookedDts;

    private LocalDateTime arrivalDts;

    private LocalDateTime  billedDts;

    private LocalDateTime  checkinDts;
    private LocalDateTime  checkoutDts;
    private LocalDateTime  cancelledtDts;
    private String comments;

    private String firstName;
    private String lastName;
    private String initial;
    private String email;
    private LocalDateTime  todayDts;

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

    private Integer adultCount;
    private Integer childrenCount;
    private Integer roomType;

    private String roomTypeName;
    private String statusName;
    private Double roomRent;
    private String floorName;
    private Integer floorNumber;

    private String  checkinDateString;
    private String  checkoutDateString;
    private String totalAmountInWords;
}
