package com.mktpc.marketPlace.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name ="client_id")
    @JsonBackReference
    private Client client;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();
    private double totalPrice;
    private boolean orderFinish;

    public Order(UUID uuid, Client client, List<OrderItem> productList, double totalPrice, boolean status) {
        this.uuid = uuid;
        this.client = client;
        this.orderItems = new ArrayList<>();
        this.totalPrice = totalPrice;
        this.orderFinish = status;
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

    public boolean isOrderFinish() {
        return orderFinish;
    }

    public void setOrderFinish(boolean orderFinish) {
        this.orderFinish = orderFinish;
    }
}
