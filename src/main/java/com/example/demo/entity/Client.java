
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "client")
@Data
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String initial;
    private String email;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String idNumber;

    @Column(nullable = false)
    private String Address1;
    private String Address2;

    private String city;
    private String state;

    @Column(nullable = false)
    private Integer pinCode;

    @Column(name = "status")
    private Integer status;


    @OneToOne
    @JoinColumn(name = "id", unique = true) // FK column in client table
    private transient ClientAttachment clientAttachment;





    // Getters and Setters
}
// Full implementation will include CRUD and JWT logic