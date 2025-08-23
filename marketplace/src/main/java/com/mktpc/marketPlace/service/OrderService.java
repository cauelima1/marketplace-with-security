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

    public OrderItem issueOrder (Long idProduct, OrderDtoRequest orderDtoRequest){
        Client client = clientRepository.findByName(clientService.getLogin());
        if(client == null) {
            clientService.firstLogin(0D);
        }
        assert client != null;
        List<Order> allOrderClient = client.getOrders();

        List<Order> ordersFinished = allOrderClient.stream().filter(Order::isOrderFinish).toList();

        if (allOrderClient.isEmpty()) {
            return addProductsToNewOrder(idProduct, orderDtoRequest.quant());
        } else if (!allOrderClient.isEmpty() && ordersFinished.isEmpty()){
            return addProductsToExistingOrder(idProduct, orderDtoRequest.quant());
        } else if (!ordersFinished.isEmpty()){
            return addProductsToNewOrder(idProduct, orderDtoRequest.quant());
        }
        return null;
        }


    public OrderItem addProductsToNewOrder (Long idProduct, Long quant){
        if (productRepository.existsById(idProduct)){
            Order newOrder = new Order();
            newOrder.setClient(clientRepository.findByName(clientService.getLogin()));
            OrderItem item = addItemToOrder(idProduct, quant);
            newOrder.addOrderItem(item);
            newOrder.setTotalPrice(item.getSubtotal());
            orderRepository.save(newOrder);
            return item;
        } else {
            throw new RuntimeException("Id product not exist.");
        }

    }

    public OrderItem addProductsToExistingOrder (Long idProduct, Long quant){
        if (productRepository.existsById(idProduct)) {
                Order existingOrder = orderRepository.findByClientName(clientService.getLogin());
                OrderItem item = addItemToOrder(idProduct, quant);
                existingOrder.addOrderItem(item);
                existingOrder.setTotalPrice(item.getSubtotal() + existingOrder.getTotalPrice());
                orderRepository.save(existingOrder);
                return item;
        } else {
            throw new RuntimeException("Inexisting Id Product.");
        }
    }

    public void saveOrderInClient(Order newOrder){
        Client client = clientRepository.findByName(clientService.getLogin());
        client.addOrder(newOrder);
        clientRepository.save(client);
    }

    public OrderItem addItemToOrder (Long idProduct, Long quant){
        if (productRepository.findById(idProduct).isPresent()){
            Product product = productRepository.findById(idProduct).get();
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuant(quant);
            item.setUnitprice(product.getPriceProduct());
            itemRepository.save(item);
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
