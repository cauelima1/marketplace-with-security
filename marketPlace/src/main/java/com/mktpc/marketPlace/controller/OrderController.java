package com.mktpc.marketPlace.controller;

import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.dtos.OrderDTO;
import com.mktpc.marketPlace.model.dtos.OrderDtoResponse;
import com.mktpc.marketPlace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{idProduct}")
    public ResponseEntity order (@PathVariable Long idProduct, @RequestBody OrderDTO orderDTO){
        Order order = orderService.issueOrder(idProduct, orderDTO);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders (){
        return ResponseEntity.ok().body(orderService.getOrders());
    }
 }
