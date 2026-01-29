package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import com.example.demo.entity.ClientAttachment;
import com.example.demo.service.ClientService;
import com.example.demo.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Client client1 = clientService.addClient(client,clientAttachment);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name(), AppConstants.STATUS_SUCCESS));
    }

    @GetMapping("/getClientList")
    public ResponseEntity<List<Client>> getClientList(@RequestHeader(value = "fromDate",required = false) String fromDate,@RequestHeader(value = "toDate",required = false) String toDate){

        if(Objects.nonNull(fromDate) && Objects.nonNull(toDate))
            return ResponseEntity.ok(clientService.getClientListByDateFilter(fromDate,toDate));
        else
            return ResponseEntity.ok(clientService.getClientList());
    }

    @GetMapping("/getClientByID")
    public ResponseEntity<Optional<Client>> getClientByID(@RequestHeader("id") Long id){
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/getClientByMobileNumber")
    public ResponseEntity<Optional<Client>> getClientByMobileNumber(@RequestHeader("mobileNumber") String mobileNumber){
        return ResponseEntity.ok(clientService.getClientByMobileNumber(mobileNumber));
    }

    @PostMapping("/createClientAndBooking")
    public ResponseEntity<ApiResponse> createClientAndBooking(@RequestPart("clientObject") @Valid Client client, @RequestPart("bookingObject") @Valid Booking booking,@RequestPart("files") MultipartFile file){

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
        client.setClientAttachment(clientAttachment);
        ApiResponse apiResponse = new ApiResponse("200 OK",clientService.createClientAndBooking(client,booking,clientAttachment));
        return  ResponseEntity.ok(apiResponse);
    }
}// Full implementation will include CRUD and JWT logic