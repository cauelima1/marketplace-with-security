package com.mktpc.marketPlace.controller;


import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.dtos.dtosResponse.ClientDtoResponse;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.service.clientServices.ClientOrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientOrderService clientService;

    @GetMapping
    public ResponseEntity<?> testePage (HttpServletRequest request){
        return ResponseEntity.ok("Authenticated user: " + clientService.getLogin());
    }

    @PostMapping("/deposit/{value}")
    public ResponseEntity<Client> clientDeposit (@PathVariable Double value){
        if(!clientRepository.existsByName(clientService.getLogin())){
            clientService.firstLogin(value);
        } else {
            clientService.depositToClient(value);
        }
        return ResponseEntity.ok().body(clientRepository.findByName(clientService.getLogin()));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, ClientDtoResponse>> clients(){
        return ResponseEntity.ok().body(clientService.getClientsDto());
    }

}
