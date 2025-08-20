package com.mktpc.marketPlace.model.dtos.dtosResponse;

public class ClientDtoResponse {

    private String name;
    private Double balance;
    private OrderDtoResponse orderDtoResponse;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public ClientDtoResponse(String name, OrderDtoResponse orderDtoResponse, Double balance) {
        this.name = name;
        this.orderDtoResponse = orderDtoResponse;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderDtoResponse getOrderDtoResponse() {
        return orderDtoResponse;
    }

    public void setOrderDtoResponse(OrderDtoResponse orderDtoResponse) {
        this.orderDtoResponse = orderDtoResponse;
    }
}
