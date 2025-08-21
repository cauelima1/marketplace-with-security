package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.dtosRequest.ProductDTO;
import com.mktpc.marketPlace.model.dtos.dtosRequest.QuantDeleteDTO;
import com.mktpc.marketPlace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct (ProductDTO productDTO){
        Product product = new Product();
        product.setId(product.getId());
        product.setName(productDTO.name());
        product.setProductDetails(productDTO.productDetails());
        product.setCategoryProduct(productDTO.categoryProduct());
        product.setPriceProduct(productDTO.priceProduct());
        product.setStock(productDTO.quant());
        return productRepository.save(product);
    }

    public List<Product> showAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> removeProduct (Long idProduct) {
        if (productRepository.existsById(idProduct)) {
            productRepository.deleteById(idProduct);
            return productRepository.findAll();
        } else {
            throw new RuntimeException("Id do not exists.");
        }
    }

    public Product removeQuant (Long idProduct, QuantDeleteDTO quantDeleteDTO){
        if (productRepository.existsById(idProduct)){
            Product product = productRepository.findById(idProduct).get();
            if(product.getStock()>=quantDeleteDTO.quant() && quantDeleteDTO.quant() > 0){
                Long balance = product.getStock() - quantDeleteDTO.quant();
                product.setStock(balance);
                productRepository.save(product);
                return product;
            } else {
                throw new RuntimeException("insufficient quantity");
            }
        } else {
            throw new RuntimeException("Id do not exists.");
        }
    }
}
