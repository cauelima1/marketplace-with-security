package com.mktpc.marketPlace.service.clientServices;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientBillService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientOrderService clientOrderService;

    public void finishOrder (Order order) {
        Client client = clientRepository.findByName(order.getClient().getName());
        if (order.getTotalPrice() <= client.getBalance()){
            client.setBalance(client.getBalance() - order.getTotalPrice());
            order.setOrderFinish(true);
            clientRepository.save(client);
        } else {
            throw new RuntimeException("insufficient money. R$" + client.getBalance() + " available.");
        }
    }

}
