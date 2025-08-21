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
    private String name;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order finalizedOrder;
    private LocalDate orderTime;

    public Delivery(Order finalizedOrder, LocalDate orderTime) {
          this.finalizedOrder = finalizedOrder;
        this.orderTime = orderTime;
    }

    public Order getFinalizedOrder(){
        return finalizedOrder;
    }

    public LocalDate getOrderTime(){
        return orderTime;
    }

    public Delivery () {

    }





}
