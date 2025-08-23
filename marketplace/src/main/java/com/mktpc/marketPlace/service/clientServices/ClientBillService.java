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

import java.util.ArrayList;
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

        Optional<Order> order = orderRepository.findById(orderId);

        if(order.isPresent() && !order.get().isOrderFinish()) {
            Client client = clientRepository.findByName(clientOrderService.getLogin());
            List<OrderItem> itemsInOrder = order.get().getOrderItems();
                if (client.getBalance() >= order.get().getTotalPrice()) {
                    updateStockProducts(itemsInOrder);
                    client.setBalance(client.getBalance() - order.get().getTotalPrice());
                    orderService.saveOrderInClient(order.get());
                    return deliveryService.turnOrderIntoDelivery(order.get());
                } else {
                    throw new RuntimeException("insufficient money. R$" + client.getBalance() + " available.");
                }
            } else {
            throw new RuntimeException("Order is null. Add items to your order.");
            }
    }

    public void updateStockProducts (List<OrderItem> purchasedItens) {
    List<Product> productsStock = productRepository.findAll();
    Map<Long, Product> stockMap = productsStock.stream()
            .collect(Collectors.toMap(Product::getId, Function.identity()));

    for (OrderItem item: purchasedItens){
        Product product = stockMap.get(item.getProduct().getId());
        if (product != null) {
            if(product.getStock() >= item.getQuant()){
                long newStock = product.getStock() - item.getQuant();
                product.setStock(Math.max(newStock,0));
                productRepository.save(product);
            } else {
                throw new RuntimeException("Not enought stock for the product: " + product.getName() + " stock: " + product.getStock());
            }

        }
    }
    }
}

