
package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@Data
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roomId;
    private Long roomNumber;
    private Long clientId;
    private Double totalAmount;
    private Double amountPaid;
    private Double amountRemaining;
    private String paymentType;
    private Integer transactionStatus;

    @CreationTimestamp
    private Timestamp bookedDts;

    private Timestamp arrivalDts;

    private Timestamp checkinDts;
    private Timestamp checkoutDts;
    private Timestamp cancelledtDts;
    private String comments;

    @Column(name = "status")
    private Integer status = 1;
    // Getters and Setters
    @OneToMany(mappedBy = "booking")
    @JsonManagedReference("booking-payment")// FK column in client table
    private List<PaymentHistory> paymentHistory = new ArrayList<>();

    @OneToMany(mappedBy = "booking")
    @JsonManagedReference("booking-history")// FK column in client table
    private List<BookingHistory> bookingHistory = new ArrayList<>();
}
// Full implementation will include CRUD and JWT logic