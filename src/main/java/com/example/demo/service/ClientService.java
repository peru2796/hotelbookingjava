package com.example.demo.service;

import com.example.demo.entity.Client;
import com.example.demo.entity.ClientAttachment;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    public ResponseEntity<Object> addClient(Client client, ClientAttachment clientAttachment);

    public ResponseEntity<Object> getClientList();

    public ResponseEntity<Object> getClientById(Long id);

    ResponseEntity<Object> getClientByMobileNumber(String mobileNumber);
}
