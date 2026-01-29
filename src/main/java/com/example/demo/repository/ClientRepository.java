package com.example.demo.repository;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByMobileNumber(String mobileNumber);
    Optional<Client> findByMobileNumber(String mobileNumber);

    List<Client> findByCreatedDtsBetweenOrderByIdDesc(LocalDateTime start, LocalDateTime end);
}