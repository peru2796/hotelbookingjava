package com.example.demo.service;

import com.example.demo.entity.Client;
import com.example.demo.entity.ClientAttachment;
import com.example.demo.repository.ClientAttachmentRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientAttachmentRepository clientAttachmentRepository;

    @Override
    public ResponseEntity<Object> addClient(Client client, ClientAttachment clientAttachment) {
        Optional<Client> optionalMobileNUmber = clientRepository.findByMobileNumber(client.getMobileNumber());
        if(optionalMobileNUmber.isEmpty()){
            client.setStatus(1);
           Client c = clientRepository.save(client);
            if(null != clientAttachment){
                clientAttachment.setClientId(c.getId());
                clientAttachmentRepository.save(clientAttachment);
            }
            return new ResponseEntity<>(getClientList(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Mobile Number is already present...",HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<Object> getClientList() {
        return new ResponseEntity<>(clientRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getClientById(Long id) {
        Optional<Client> clientObj = clientRepository.findById(id);
        return new ResponseEntity<>(clientObj.isPresent()?clientObj.get():"no record found for the given id..",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getClientByMobileNumber(String mobileNumber) {
        return new ResponseEntity<>(clientRepository.findByMobileNumber(mobileNumber).isPresent(),HttpStatus.OK);
    }
}
