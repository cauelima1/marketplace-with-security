package com.mktpc.marketPlace.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="client_id")
    private Client client;
    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();
    private double totalPrice;
    private boolean orderFinish = false;


    public Order () {
    }

    public Order(Long id, Client client, List<OrderItem> orderItems, double totalPrice, boolean orderFinish) {
        this.id = id;
        this.client = client;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.orderFinish = orderFinish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOrderFinish() {
        return orderFinish;
    }
    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }


    public void setOrderFinish(boolean orderFinish) {
        this.orderFinish = orderFinish;
    }
}
