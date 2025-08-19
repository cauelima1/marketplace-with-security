package com.mktpc.marketPlace.service.clientServices;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.dtos.dtosResponse.ClientDtoResponse;
import com.mktpc.marketPlace.model.dtos.dtosResponse.OrderDtoResponse;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientOrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void firstLogin() {
        Client client = new Client(getLogin(), 0.0, null);
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

    public String getLogin () {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
