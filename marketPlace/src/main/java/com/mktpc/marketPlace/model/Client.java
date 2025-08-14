package com.mktpc.marketPlace.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_clients")
public class Client {

    @Id
    private String name;
    private Double balance;
    @OneToMany
    private List<Order> orderList;

    public Client () {

    }


    public Client(String name, Double balance, List<Order> orderList) {
        this.name = name;
        this.balance = balance;
        this.orderList = orderList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
