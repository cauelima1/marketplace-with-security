package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Delivery;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.DeliveryRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void turnOrderIntoDelivery (Order orderToFinished){
        if (orderToFinished.isOrderFinish()){
            LocalDate time = LocalDate.now();
            Delivery delivery = new Delivery(orderToFinished, time);
            deliveryRepository.save(delivery);
        }
    }

    public List<Delivery> getDeliver (){
        return deliveryRepository.findAll();
    }

}
