package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import com.example.demo.entity.ClientAttachment;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {

     Client addClient(Client client, ClientAttachment clientAttachment);

     List<Client> getClientList();
    List<Client> getClientListByDateFilter(String fromDate,String toDate);
     Optional<Client> getClientById(Long id);

    Optional<Client> getClientByMobileNumber(String mobileNumber);

     String createClientAndBooking(Client client, Booking booking,ClientAttachment clientAttachment);
}
