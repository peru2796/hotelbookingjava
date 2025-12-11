
package com.example.demo.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reportName;
    private String description;
    @Column(name = "status")
    private Integer status;
    // Getters and Setters
}
// Full implementation will include CRUD and JWT logic