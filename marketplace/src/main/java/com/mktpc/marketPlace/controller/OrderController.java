package com.mktpc.marketPlace.controller;

import com.mktpc.marketPlace.model.Delivery;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.dtos.dtosRequest.OrderDtoRequest;
import com.mktpc.marketPlace.model.dtos.dtosResponse.OrderDtoResponse;
import com.mktpc.marketPlace.service.OrderService;
import com.mktpc.marketPlace.service.clientServices.ClientBillService;
import com.mktpc.marketPlace.service.clientServices.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ClientBillService clientBillService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/{idProduct}")
    public ResponseEntity<Order> order (@PathVariable Long idProduct, @RequestBody OrderDtoRequest orderDtoRequest){
        Order order = orderService.issueOrder(idProduct, orderDtoRequest);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/finishOrder/{orderId}")
    public ResponseEntity<Delivery> finishOrder (@PathVariable Long orderId) {
        Delivery delivery = clientBillService.finishOrder(orderId);
                return ResponseEntity.ok(delivery);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrders(){
        return ResponseEntity.ok(clientOrderService.getClientOrder()); //arrumar
    }
 }
