package com.mktpc.marketPlace.model.dtos;

import com.mktpc.marketPlace.model.OrderItem;

public class OrderDtoResponse {

    private String clientName;
    private double clientBalance;
    private OrderItem orderItem;
    private Double totalPriceOrder;


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getClientBalance() {
        return clientBalance;
    }

    public void setClientBalance(double clientBalance) {
        this.clientBalance = clientBalance;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Double getTotalPriceOrder() {
        return totalPriceOrder;
    }

    public void setTotalPriceOrder(Double totalPriceOrder) {
        this.totalPriceOrder = totalPriceOrder;
    }

    public OrderDtoResponse(String clientName, double clientBalance, OrderItem orderItem, Double totalPriceOrder) {
        this.clientName = clientName;
        this.clientBalance = clientBalance;
        this.orderItem = orderItem;
        this.totalPriceOrder = totalPriceOrder;
    }
}
