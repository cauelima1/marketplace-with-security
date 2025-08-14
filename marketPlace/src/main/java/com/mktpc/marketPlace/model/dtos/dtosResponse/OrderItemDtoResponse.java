package com.mktpc.marketPlace.model.dtos.dtosResponse;
import com.mktpc.marketPlace.model.OrderItem;
import com.mktpc.marketPlace.model.Product;

public class OrderItemDtoResponse {

    private Long idProduct;
    private String productName;
    private Long quant;
    private String productDetails;
    private Double priceProduct;
    private String productCategory;

    public OrderItemDtoResponse (OrderItem orderItem){
        Product product = orderItem.getProduct();
        if (product != null){
            this.idProduct = product.getId();
            this.productName = product.getName();
            this.quant = orderItem.getQuant();
            this.priceProduct = product.getPriceProduct();
            this.productDetails = product.getProductDetails();
            this.productCategory = product.getCategoryProduct().toString();
        }
    }

    public Double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public Long getQuant() {
        return quant;
    }

    public void setQuant(Long quant) {
        this.quant = quant;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
