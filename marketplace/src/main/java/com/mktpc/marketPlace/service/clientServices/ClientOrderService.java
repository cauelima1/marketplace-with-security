package com.mktpc.marketPlace.service.clientServices;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.dtos.dtosResponse.ClientDtoResponse;
import com.mktpc.marketPlace.model.dtos.dtosResponse.OrderDtoResponse;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ClientOrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void firstLogin(Double balance) {
        Client client = new Client();
        client.setName(getLogin());
        client.setBalance(balance);
        client.setOrderList(new ArrayList<>());
        clientRepository.save(client);
    }

    public void depositToClient(Double value){
        if(value >= 0) {
            Client client = clientRepository.findByName(getLogin());
            client.setBalance(client.getBalance() + value);
            clientRepository.save(client);
        } else {
            throw new RuntimeException("Invalid deposit value.");
        }
    }

    public void deleteUserOrder (UUID orderId) {
        if (orderRepository.findByUuid(orderId) != null) {
            orderRepository.delete(orderRepository.findByUuid(orderId));
        } else {
            throw new RuntimeException("Inform a valid Order ID");
        }
    }

    public List<Client> getClients (){
        return clientRepository.findAll();
    }

    public Map<String ,  ClientDtoResponse> getClientsDto () {
        List<OrderDtoResponse> ordersDto = orderService.getOrderDTO();

        return ordersDto.stream()
                .collect(Collectors.toMap(
                        OrderDtoResponse::getClientName,
                        order -> {
                            // Busca o saldo do cliente (exemplo: via clientService ou repository)
                            Double balance = clientRepository.findByName(getLogin()).getBalance();
                            return new ClientDtoResponse(order.getClientName(), order, balance);
                        }
                ));
    }


    public String getLogin () {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
