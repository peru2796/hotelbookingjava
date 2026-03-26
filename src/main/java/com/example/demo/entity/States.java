
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "states")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class States implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "status")
    private Integer status;
    // Getters and Setters

}
// Full implementation will include CRUD and JWT logic