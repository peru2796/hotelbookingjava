package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name= "bookinghistory")
@Data
public class BookingHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "booking_id",updatable = false,insertable = false)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id")
    @JsonBackReference("booking-history")
    private Booking booking;


    private Integer bookingStatus;

    @CreationTimestamp
    private Timestamp createdDts;

    @Column(name = "status")
    private Integer status = 1;
}
