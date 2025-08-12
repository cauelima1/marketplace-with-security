package com.mktpc.marketPlace.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_clients")
public class Client {

    @Id
    private String name;
    private String email;
    private double balance;
    @OneToMany
    private List<Order> orderList;

    public Client () {

    }


    public Client(String name, String email, double balance, List<Order> orderList) {
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.orderList = orderList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
