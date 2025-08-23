package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.OrderItem;
import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.dtosRequest.OrderDtoRequest;
import com.mktpc.marketPlace.model.dtos.dtosResponse.OrderDtoResponse;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderItemRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.repository.ProductRepository;
import com.mktpc.marketPlace.service.clientServices.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {


    @Autowired
    @Lazy
    private ClientOrderService clientOrderService;

    @Autowired
    private OrderItemRepository itemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    @Lazy
    private ClientOrderService clientService;

    public Order issueOrder (Long idProduct, OrderDtoRequest orderDtoRequest){
        if(!clientRepository.existsByName(clientService.getLogin())) {
            clientService.firstLogin(0D);
        }
        List<Order> orders = orderRepository.findAll().stream().filter(o-> o.getClient().equals(clientService.getLogin())).toList();
        if (orders.isEmpty()){
            Optional<Product> stockProduct = productRepository.findById(idProduct);
            if (stockProduct.isPresent()) {
                return addProductsToNewOrder(idProduct, orderDtoRequest.quant());
            } else {
                throw new RuntimeException("Product Id does not exist.");
            }
        } else {
            return addProductsToExistingOrder(idProduct, orderDtoRequest.quant());
        }
    }

    public Order addProductsToNewOrder (Long idProduct, Long quant){
        Order newOrder = new Order();
        newOrder.setClient(clientRepository.findByName(clientService.getLogin()));
        OrderItem item = addItemToOrder(idProduct, quant);
        newOrder.getOrderItems().add(item);
        newOrder.setTotalPrice(item.getSubtotal());
        itemRepository.save(item);
        orderRepository.save(newOrder);
        saveOrderInClient(newOrder);
        return newOrder;
    }

    public Order addProductsToExistingOrder (Long idProduct, Long quant){
        if (productRepository.existsById(idProduct)) {
            Order existingOrder = orderRepository.findByClientName(clientService.getLogin());
            if (!existingOrder.isOrderFinish()) {
                OrderItem item = addItemToOrder(idProduct, quant);
                existingOrder.getOrderItems().add(item);
                existingOrder.setTotalPrice(item.getSubtotal() + existingOrder.getTotalPrice());
                itemRepository.save(item);
                orderRepository.save(existingOrder);
                saveOrderInClient(existingOrder);
                return existingOrder;
            } else {
                orderRepository.delete(existingOrder);
                throw new RuntimeException("No orders available.");
            }
        } else {
            throw new RuntimeException("Inexisting Id Product.");
        }
    }

    public void saveOrderInClient(Order newOrder){
        Client client = clientRepository.findByName(clientService.getLogin());
        client.getOrders().add(newOrder);
        clientRepository.save(client);
    }

    public OrderItem addItemToOrder (Long idProduct, Long quant){
        if (productRepository.existsById(idProduct)){
        Product product = productRepository.findById(idProduct).get();
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuant(quant);
        item.setUnitprice(product.getPriceProduct());
        return item;
        } else {
            throw new RuntimeException("Inexisting Id Product.");
        }
    }


    public Order getUserOrder (){
      return orderRepository.findByClientName(clientService.getLogin());
    }

    public List<Order> getOpenOrders(){
        return orderRepository.findAll().stream().filter(o-> !getUserOrder().isOrderFinish()).toList();
    }

    public List<OrderDtoResponse> getOrderDTO (){
        List<Order> orders = getOpenOrders();
        return orders.stream()
                .map(OrderDtoResponse::new)
                .collect(Collectors.toList());
    }
}
