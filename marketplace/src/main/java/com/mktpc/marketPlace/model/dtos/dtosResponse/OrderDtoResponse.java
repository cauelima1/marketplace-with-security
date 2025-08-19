package com.mktpc.marketPlace.model.dtos.dtosResponse;

import com.mktpc.marketPlace.model.Order;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class OrderDtoResponse {
    private UUID uuid;
    private String clientName;
    private List<OrderItemDtoResponse> orderItem;
    private Double totalPriceOrder;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<OrderItemDtoResponse> getItems() {
        return orderItem;
    }

    public void setItems(List<OrderItemDtoResponse> items) {
        this.orderItem = orderItem;
    }

    public Double getTotalPriceOrder() {
        return totalPriceOrder;
    }

    public void setTotalPriceOrder(Double totalPriceOrder) {
        this.totalPriceOrder = totalPriceOrder;
    }

    public  OrderDtoResponse  (Order order) {
        if (order.getClient() != null) {
            this.clientName = order.getClient().getName();
            this.uuid = order.getUuid();
        }
        this.totalPriceOrder = order.getTotalPrice();

        if(order.getOrderItems() != null){
            this.orderItem = order.getOrderItems()
                    .stream()
                    .map(OrderItemDtoResponse::new)
                    .collect(Collectors.toList());
        }
    }
}
