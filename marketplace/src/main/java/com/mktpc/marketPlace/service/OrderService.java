package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.OrderItem;
import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.dtosRequest.ItemDeleteDTORequest;
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
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {


    @Autowired
    @Lazy
    private ClientOrderService clientOrderService;

    @Autowired
    private OrderItemRepository orderItemRepository;

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

    public OrderItem issueOrder(Long idProduct, OrderDtoRequest orderDtoRequest) {
        if (!clientRepository.existsByName(clientOrderService.getLogin())) {
            clientService.firstLogin(0D);
        }
        Client client = clientRepository.findByName(clientOrderService.getLogin());
        List<Order> orderOpen = client.getOrders().stream().filter(o-> !o.isOrderFinish()).toList();

        if (orderOpen.isEmpty()) {
            return addProductsToNewOrder(idProduct, orderDtoRequest.quant());
        } else  {
            return addProductsToExistingOrder(idProduct, orderDtoRequest.quant());
        }
    }

    public OrderItem addProductsToNewOrder (Long idProduct, Long quant){

        if (productRepository.existsById(idProduct)){
            Order newOrder = new Order();
            newOrder.setClient(clientRepository.findByName(clientService.getLogin()));
            OrderItem item = addItemToOrder(newOrder, idProduct, quant);
            newOrder.addOrderItem(item);
            newOrder.setTotalPrice(item.getSubtotal());
            orderRepository.save(newOrder);
            return item;
        } else {
            throw new RuntimeException("Id product not exist.");
        }
    }

    public OrderItem addProductsToExistingOrder (Long idProduct, Long quant){

        Order existingOrder = orderRepository.findAll().stream().filter(o->
                !o.isOrderFinish() && o.getClient().getName().equals(clientOrderService.getLogin()))
                .findFirst()
                .orElse(null);

        if (existingOrder != null)
            if (productRepository.existsById(idProduct)) {
                OrderItem item = addItemToOrder(existingOrder, idProduct, quant);
                existingOrder.addOrderItem(item);
                existingOrder.setTotalPrice(item.getSubtotal() + existingOrder.getTotalPrice());
                orderRepository.save(existingOrder);
                return item;
            } else {
                throw new RuntimeException("Inexisting Id Product.");
            } else {
                throw new RuntimeException("Order error.");
        }
    }

    public void saveOrderInClient(Order newOrder){
        Client client = clientRepository.findByName(clientService.getLogin());
        client.addOrder(newOrder);
        clientRepository.save(client);
    }

    public OrderItem addItemToOrder (Order order, Long idProduct, Long quant){
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

    @Transactional
    public void deledeOrder (Long orderId){
        Order order = orderRepository.findAll().stream().filter(o-> o.getId().equals(orderId)).findFirst().orElse(null);
        if (order != null && order.getClient().getName().equals(clientOrderService.getLogin())){
            if(!order.isOrderFinish())
                orderRepository.delete(order);
            else
                throw new RuntimeException("Order already closed.");
        } else {
            throw new RuntimeException("Order Id not exists.");
        }
    }

    @Transactional
    public Order deleteItem(Long orderId, ItemDeleteDTORequest itemDelete){
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<OrderItem> itemToRemove = orderItemRepository.findById(itemDelete.itemId());
        if (order.isPresent()) {
            if(itemToRemove.isPresent() && order.get().getClient().getName().equals(clientOrderService.getLogin())){
                if (!order.get().isOrderFinish()) {
                    order.get().getOrderItems().removeIf(item -> item.getId().equals(itemDelete.itemId()));
                    orderItemRepository.delete(itemToRemove.get());
                    return orderRepository.save(order.get());
                } else {
                    throw new RuntimeException("Order already closed.");
                }
            } else{
                throw new RuntimeException("Item Id not exists.");
            }
        } else {
            throw new RuntimeException("Order id not exists.");
        }

    }
}
