
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "clientAttachment")
@Data
public class ClientAttachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clientId;



//    @JoinColumn(name = "client_id")  // FK column
//    @MapsId                         // Share PK from the client association
//    private Client client;


    private byte[] clientImage;

    private String fileName;

    private String fileType;

    @Column(name = "status")
    private Integer status;
    // Getters and Setters
}
// Full implementation will include CRUD and JWT logic