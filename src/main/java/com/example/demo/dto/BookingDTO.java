package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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

    private Timestamp bookedDts;

    private Timestamp arrivalDts;

    private Timestamp checkinDts;
    private Timestamp checkoutDts;
    private Timestamp cancelledtDts;
    private String comments;

    private String firstName;
    private String lastName;
    private String initial;
    private String email;


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
}
