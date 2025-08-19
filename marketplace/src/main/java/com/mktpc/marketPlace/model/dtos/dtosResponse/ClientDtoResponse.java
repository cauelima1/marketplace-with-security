package com.mktpc.marketPlace.model.dtos.dtosResponse;

public class ClientDtoResponse {

    private String name;
    private OrderDtoResponse orderDtoResponse;

    public ClientDtoResponse(String name, OrderDtoResponse orderDtoResponse) {
        this.name = name;
        this.orderDtoResponse = orderDtoResponse;
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
