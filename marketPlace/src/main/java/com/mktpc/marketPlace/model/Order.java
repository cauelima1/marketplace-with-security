package com.mktpc.marketPlace.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @OneToOne
    private Client client;
    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();
    private double totalPrice;
    private boolean orderStatus;

    public Order(UUID uuid, Client client, List<OrderItem> productList, double totalPrice, boolean status) {
        this.uuid = uuid;
        this.client = client;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.orderStatus = status;
    }

    public Order() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public void setorderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }
}
