package com.mktpc.marketPlace.controller;


import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.service.clientServices.ClientOrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if (clientRepository.existsByName(clientService.getLogin())){
            Client client = clientService.depositToClient(value);
            return ResponseEntity.ok().body(client);
        } else {
            Client client = clientService.firstLogin(value);
            return ResponseEntity.ok().body(client);
        }
    }



}
