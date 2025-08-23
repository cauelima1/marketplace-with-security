package com.mktpc.marketPlace.model;

import com.mktpc.marketPlace.repository.DeliveryRepository;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name ="db_delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order finalizedOrder;
    private LocalDate orderTime;

    public Delivery(){

    }

    public Delivery(Order finalizedOrder, LocalDate orderTime) {
          this.finalizedOrder = finalizedOrder;
        this.orderTime = orderTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Order getFinalizedOrder() {
        return finalizedOrder;
    }

    public void setFinalizedOrder(Order finalizedOrder) {
        this.finalizedOrder = finalizedOrder;
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public Delivery(UUID id, Order finalizedOrder, LocalDate orderTime) {
        this.id = id;
        this.finalizedOrder = finalizedOrder;
        this.orderTime = orderTime;
    }
}
