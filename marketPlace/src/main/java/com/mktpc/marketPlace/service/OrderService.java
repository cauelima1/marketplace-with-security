package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Order;
import com.mktpc.marketPlace.model.OrderItem;
import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.OrderDTO;
import com.mktpc.marketPlace.model.dtos.QuantDeleteDTO;
import com.mktpc.marketPlace.repository.ClientRepository;
import com.mktpc.marketPlace.repository.OrderItemRepository;
import com.mktpc.marketPlace.repository.OrderRepository;
import com.mktpc.marketPlace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
    private ClientService clientService;

    public Order issueOrder (Long idProduct, OrderDTO orderDTO){
        Optional<Product> stockProduct = productRepository.findById(idProduct);

        if(stockProduct.isPresent()) {
            uptateExistingProductStock(stockProduct.get(), orderDTO.quant());

            if (orderRepository.existsByClientName(clientService.getLogin())){
                return addProductsToExistingOrder(idProduct, orderDTO.quant());
            } else {
                return addProductsToNewOrder(idProduct, orderDTO.quant());
            }
        } else {
            throw new RuntimeException ("Product Id does not exist.");
        }
    }

    public Order addProductsToNewOrder (Long idProduct, Long quant){
        Order newOrder = new Order();
        newOrder.setClient(clientRepository.findByName(clientService.getLogin()));

        Product product = productRepository.findById(idProduct).get();
        OrderItem item = new OrderItem();

        item.setProduct(product);
        item.setQuant(quant);
        item.setUnitprice(product.getPriceProduct());

        newOrder.getOrderItems().add(item);
        newOrder.setTotalPrice(item.getSubtotal());
        itemRepository.save(item);
        return orderRepository.save(newOrder);
    }

    public Order addProductsToExistingOrder (Long idProduct, Long quant){
        Order existingOrder = orderRepository.findByClientName(clientService.getLogin());
        Product product = productRepository.findById(idProduct).get();

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuant(quant);
        item.setUnitprice(product.getPriceProduct());

        existingOrder.getOrderItems().add(item);
        existingOrder.setTotalPrice(item.getSubtotal() + existingOrder.getTotalPrice());

        itemRepository.save(item);
        return orderRepository.save(existingOrder);
    }

    public void uptateExistingProductStock(Product stockProduct, Long quant){
        if (stockProduct.getQuant() >= quant) {
            QuantDeleteDTO quantDeleteDTO = new QuantDeleteDTO(quant);
            productService.removeQuant(stockProduct.getId(), quantDeleteDTO);
        } else {
            throw new RuntimeException("Insifficiant products stock");
        }
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }
}
