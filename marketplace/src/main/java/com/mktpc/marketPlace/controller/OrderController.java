package com.mktpc.marketPlace.controller;

import com.mktpc.marketPlace.model.Client;
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
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> order (@PathVariable Long idProduct, @RequestBody OrderDtoRequest orderDtoRequest){
        orderService.issueOrder(idProduct, orderDtoRequest);
        List<OrderDtoResponse> orderDtoResponse = orderService.getOrders().stream().map(OrderDtoResponse::new).toList();
                return ResponseEntity.ok().body(orderDtoResponse.stream().filter(c-> c.getClientName().equals(clientOrderService.getLogin())));
    }

    @GetMapping("/finishOrder")
    public ResponseEntity<Order> finishOrder () {
        clientBillService.finishOrder(orderService.getUserOrder());
        return ResponseEntity.ok(orderService.getUserOrder());
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<List<OrderDtoResponse>> deleteOrderByUUID (@PathVariable UUID orderId){
        clientOrderService.deleteUserOrder(orderId);
        return ResponseEntity.ok(orderService.getOrderDTO());
    }

    @GetMapping
    public ResponseEntity<List<OrderDtoResponse>> getAllOrders (){
        return ResponseEntity.ok(orderService.getOrderDTO());
    }
 }
