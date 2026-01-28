
package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "billing")
@Data
public class Billing  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookingNumber;
    private Long bookingId;
    private Long roomId;
    private Long roomNumber;
    private Long clientId;
    private Double totalAmount;
    private Double amountPaid;
    private Double amountRemaining;
    private Double baseFare;
    private Double sgst;
    private Double cgst;
    private String paymentType;
    private Integer transactionStatus;


    private LocalDateTime  bookedDts;

    @CreationTimestamp
    private LocalDateTime  billedDts;

    private LocalDateTime checkinDts;
    private LocalDateTime  checkoutDts;

    private String comments;

    private Integer adultCount;
    private Integer childrenCount;
    private Integer roomType;

    @Column(name = "status")
    private Integer status = 1;
    // Getters and Setters

}
// Full implementation will include CRUD and JWT logic