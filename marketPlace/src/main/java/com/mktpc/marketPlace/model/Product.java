package com.mktpc.marketPlace.model;

import com.mktpc.marketPlace.service.ProductService;
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
    private int quant;
    private double priceProduct;

    public Product (){
    }

    public Product(Long id, String name, String productDetails, CategoryProduct categoryProduct, int quant, double priceProduct) {
        this.id = id;
        this.name = name;
        this.productDetails = productDetails;
        this.categoryProduct = categoryProduct;
        this.quant = quant;
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

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }
}
