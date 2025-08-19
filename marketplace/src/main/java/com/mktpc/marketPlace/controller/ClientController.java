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
    private ClientOrderService clientService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public ResponseEntity<?> testePage (HttpServletRequest request){
        return ResponseEntity.ok("Authenticated user: " + clientService.getLogin());
    }

    @PostMapping("/deposit/{value}")
    public ResponseEntity<List<Client>> clientDeposit (@PathVariable Double value){
        clientOrderService.firstLogin();
        clientService.depositToClient(value);
        return ResponseEntity.ok().body(clientService.getClients());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> clients(){
        return ResponseEntity.ok().body(clientService.getClients());
    }

}
