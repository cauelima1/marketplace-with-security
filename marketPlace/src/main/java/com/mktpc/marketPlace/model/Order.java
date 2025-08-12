package com.mktpc.marketPlace.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @ManyToOne
    private Client client;
    @OneToMany
    private List<Product> productList;
    private double totalPrice;
    private boolean status;

    public Order(UUID uuid, Client client, List<Product> productList, double totalPrice, boolean status) {
        this.uuid = uuid;
        this.client = client;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.status = status;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
