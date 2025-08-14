package com.mktpc.marketPlace.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String productDetails;

    @Enumerated(EnumType.STRING)
    private CategoryProduct categoryProduct;
    private Long stock;
    private double priceProduct;

    public Product (){
    }

    public Product(Long id, String name, String productDetails, CategoryProduct categoryProduct, Long stock, double priceProduct) {
        this.id = id;
        this.name = name;
        this.productDetails = productDetails;
        this.categoryProduct = categoryProduct;
        this.stock = stock;
        this.priceProduct = priceProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public CategoryProduct getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(CategoryProduct categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }
}
