package com.mktpc.marketPlace.service.clientServices;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.OrderItem;
import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderItemRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.repository.ProductRepository;
import com.mktpc.marketPlace.service.DeliveryService;
import com.mktpc.marketPlace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClientBillService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientOrderService clientOrderService;

    public void finishOrder (Order order) {
        Client client = clientRepository.findByName(order.getClient().getName());
            if (order.getTotalPrice() <= client.getBalance()){
                client.setBalance(client.getBalance() - order.getTotalPrice());
                order.setOrderFinish(true);

                List<OrderItem> purchasedItens = order.getOrderItems();
                List<Product> productsStock = productRepository.findAll();

                updateStockProducts(purchasedItens, productsStock);

                deliveryService.turnOrderIntoDelivery(order);
                clientRepository.save(client);
            } else {
                throw new RuntimeException("insufficient money. R$" + client.getBalance() + " available.");
            }
    }

    public void updateStockProducts (List<OrderItem> purchasedItens, List<Product> productsStock) {
    Map<Long, Product> stockMap = productsStock.stream()
            .collect(Collectors.toMap(Product::getId, Function.identity()));

    for (OrderItem item: purchasedItens){
        Product product = stockMap.get(item.getProduct().getId());
        if (product != null) {
            long newStock = product.getStock() - item.getQuant();
            product.setStock(Math.max(newStock,0));
            productRepository.save(product);
        }
    }


    }
}

