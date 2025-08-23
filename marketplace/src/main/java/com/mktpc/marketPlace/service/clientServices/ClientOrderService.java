package com.mktpc.marketPlace.service.clientServices;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.dtos.dtosResponse.ClientDtoResponse;
import com.mktpc.marketPlace.model.dtos.dtosResponse.OrderDtoResponse;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class ClientOrderService {


    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Client firstLogin(Double balance) {
        Client client = new Client();
        client.setName(getLogin());
        client.setBalance(balance);
        return clientRepository.save(client);
    }

    public Client depositToClient(Double value){
        if(value >= 0) {
            Client client = clientRepository.findByName(getLogin());
            client.setBalance(client.getBalance() + value);
            clientRepository.save(client);
            return client;
        } else {
            throw new RuntimeException("Invalid deposit value.");
        }
    }

    public Map<String ,  ClientDtoResponse> getClientsDto () {
        List<OrderDtoResponse> ordersDto = orderService.getOrderDTO();

        return ordersDto.stream()
                .collect(Collectors.toMap(
                        OrderDtoResponse::getClientName,
                        order -> {
                            Double balance = clientRepository.findByName(getLogin()).getBalance();
                            return new ClientDtoResponse(order.getClientName(), order, balance);
                        }
                ));
    }

    public List<Order> getClientOrder(){
        Client client = clientRepository.findByName(getLogin());
        return client.getOrders().stream().filter(o-> !o.isOrderFinish()).toList();
    }

    public String getLogin () {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
