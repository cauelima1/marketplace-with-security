package com.mktpc.marketPlace.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;
    private Long quant;
    private Double unitPrice;

    public OrderItem(){

    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productToOrderDTO) {
        this.product = productToOrderDTO;
    }

    public Long getQuant() {
        return quant;
    }

    public void setQuant(Long quant) {
        this.quant = quant;
    }

    public Double getUnitprice() {
        return unitPrice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitPrice = unitprice;
    }

    public Double getSubtotal(){
        return unitPrice * quant;
    }

}
