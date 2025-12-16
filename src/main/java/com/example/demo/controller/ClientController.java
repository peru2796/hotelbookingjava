package com.example.demo.controller;

import com.example.demo.entity.Client;
import com.example.demo.entity.ClientAttachment;
import com.example.demo.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ClientController {


    @Autowired
    ClientService clientService;


    @PostMapping( path = "/addClient", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(
            @RequestPart("clientObject") @Valid Client client,
            @RequestPart("files") MultipartFile file // use MultipartFile for single; List for many
    ) {
        ClientAttachment clientAttachment = null;
        // Validate files
        if (null != file) {
            clientAttachment = new ClientAttachment();
            clientAttachment.setFileName(file.getOriginalFilename());
            clientAttachment.setFileType(file.getContentType());
            clientAttachment.setStatus(1);
            try {
                clientAttachment.setClientImage(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("failed to load the file" ,e);
            }
        }
        return clientService.addClient(client,clientAttachment);

    }

    @GetMapping("/getClientList")
    public ResponseEntity<Object> getClientList(){
        return clientService.getClientList();
    }

    @GetMapping("/getClientByID")
    public ResponseEntity<Object> getClientByID(@RequestHeader("id") Long id){
        return clientService.getClientById(id);
    }

    @GetMapping("/getClientByMobileNumber")
    public ResponseEntity<Object> getClientByMobileNumber(@RequestHeader("mobileNumber") String mobileNumber){
        return clientService.getClientByMobileNumber(mobileNumber);
    }
}// Full implementation will include CRUD and JWT logic