
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "rooms")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer floorNumber;


    @Column(unique = true, nullable = false)
    private Integer roomNumber;

    @Column(unique = true, nullable = false)
    private String roomName;


    @Column(name = "status")
    private Integer status;
    // Getters and Setters
}
// Full implementation will include CRUD and JWT logic