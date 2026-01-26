
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "billing_seqno_generator")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingSeqNoGenerator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String year;

    @Column(unique = true, nullable = false)
    private Integer last_value;

    @Column(name = "status")
    private Integer status;
    // Getters and Setters
}
// Full implementation will include CRUD and JWT logic