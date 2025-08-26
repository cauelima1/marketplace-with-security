package com.mktpc.marketPlace.service.clientServices;

import com.mktpc.marketPlace.model.*;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.repository.ProductRepository;
import com.mktpc.marketPlace.service.DeliveryService;
import com.mktpc.marketPlace.service.OrderService;
import com.mktpc.marketPlace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClientBillService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientOrderService clientOrderService;

    public Delivery finishOrder (Long orderId) {
        Client client = clientRepository.findByName(clientOrderService.getLogin());
        Optional<Order> order = orderRepository.findById(orderId);

        if(order.isPresent() && !order.get().isOrderFinish()){
            if(order.get().getClient().getName().equals(client.getName())) {
                List<OrderItem> itemsInOrder = order.get().getOrderItems();
                    if (client.getBalance() >= order.get().getTotalPrice()) {
                        updateStockProducts(itemsInOrder);
                        client.setBalance(client.getBalance() - order.get().getTotalPrice());
                        order.get().setOrderFinish(true);
                        orderService.saveOrderInClient(order.get());
                        return deliveryService.turnOrderIntoDelivery(order.get());
                    } else {
                        throw new RuntimeException("insufficient money. R$" + client.getBalance() + " available.");
                    }
                } else {
            throw new RuntimeException("Order is null. Add items to your order.");
            }
        } else {
            throw new RuntimeException("Order already closed, try another one.");
        }
    }

    @Transactional
    public void updateStockProducts (List<OrderItem> purchasedItens) {
       Map<Long, Product> stockMap = productRepository.findAll().stream()
            .collect(Collectors.toMap(Product::getId, Function.identity()));

       try {
           for (OrderItem item : purchasedItens) {
               Product product = stockMap.get(item.getProduct().getId());
               if (product != null) {
                   if (product.getStock() >= item.getQuant()) {
                       long newStock = product.getStock() - item.getQuant();
                       product.setStock(Math.max(newStock, 0));
                       productRepository.save(product);
                   } else {
                       throw new RuntimeException("Not enought stock for the product: " + product.getName() + " stock: " + product.getStock());
                   }
               }
           }
       } catch (RuntimeException e){
           throw new RuntimeException("Verify stock!");
       }
    }
}

