package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.OrderItem;
import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.dtosRequest.OrderDtoRequest;
import com.mktpc.marketPlace.model.dtos.dtosRequest.QuantDeleteDTO;
import com.mktpc.marketPlace.model.dtos.dtosResponse.OrderDtoResponse;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderItemRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.repository.ProductRepository;
import com.mktpc.marketPlace.service.clientServices.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderService {

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
    private ClientOrderService clientService;

    public Order issueOrder (Long idProduct, OrderDtoRequest orderDtoRequest){
        Optional<Product> stockProduct = productRepository.findById(idProduct);

        if(stockProduct.isPresent()) {
            uptateExistingProductStock(stockProduct.get(), orderDtoRequest.quant());

            if (orderRepository.existsByClientName(clientService.getLogin())){
                return addProductsToExistingOrder(idProduct, orderDtoRequest.quant());
            } else {
                return addProductsToNewOrder(idProduct, orderDtoRequest.quant());
            }
        } else {
            throw new RuntimeException ("Product Id does not exist.");
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
        Order existingOrder = orderRepository.findByClientName(clientService.getLogin());

        OrderItem item = addItemToOrder(idProduct, quant);

        existingOrder.getOrderItems().add(item);
        existingOrder.setTotalPrice(item.getSubtotal() + existingOrder.getTotalPrice());

        itemRepository.save(item);
        orderRepository.save(existingOrder);
        saveOrderInClient(existingOrder);
        return existingOrder;
    }

    public void saveOrderInClient(Order order){
        Client client = clientRepository.findByName(clientService.getLogin());
        List<Order> orderClient = new ArrayList<>();
        orderClient.add(orderRepository.findByClientName(client.getName()));
        client.setOrderList(orderClient);
        clientRepository.save(client);
    }

    public OrderItem addItemToOrder (Long idProduct, Long quant){
        Product product = productRepository.findById(idProduct).get();

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuant(quant);
        item.setUnitprice(product.getPriceProduct());
        return item;
    }

    public void uptateExistingProductStock(Product stockProduct, Long quant){
        if (stockProduct.getStock() >= quant) {
            QuantDeleteDTO quantDeleteDTO = new QuantDeleteDTO(quant);
            productService.removeQuant(stockProduct.getId(), quantDeleteDTO);
        } else {
            throw new RuntimeException("Insifficiant products stock");
        }
    }

    public Order getUserOrder (){
        return orderRepository.findByClientName(clientService.getLogin());
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public List<OrderDtoResponse> getOrderDTO (){
        List<Order> orders = getOrders();

        List<OrderDtoResponse> dtos = orders.stream()
                .map(OrderDtoResponse::new)
                .collect(Collectors.toList());
        return dtos;
    }


}
