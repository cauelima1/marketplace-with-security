package com.mktpc.marketPlace.controller;

import com.mktpc.marketPlace.model.Delivery;
import com.mktpc.marketPlace.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/delivery")
@RestController
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<Delivery>> showDeliver (){
        return ResponseEntity.ok().body(deliveryService.getDeliver());
    }

}
