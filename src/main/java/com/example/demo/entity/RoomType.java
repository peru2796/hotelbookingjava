
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "roomtype")
@Data
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String roomType;

    @Column
    private String description;

    @Column
    private Double amount;

    @Column(name = "status")
    private Integer status;
    // Getters and Setters
}
// Full implementation will include CRUD and JWT logic