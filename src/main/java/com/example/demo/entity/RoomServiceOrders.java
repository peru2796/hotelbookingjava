
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "room_service_orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomServiceOrders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long bookingId;

    @Column
    private String orderName;

    @Column
    private String orderValue;

    @Column(name = "status")
    private Integer status =1;
    // Getters and Setters
}
// Full implementation will include CRUD and JWT logic