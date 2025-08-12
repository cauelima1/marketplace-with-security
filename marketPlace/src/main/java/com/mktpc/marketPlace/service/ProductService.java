package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.ProductDTO;
import com.mktpc.marketPlace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct (ProductDTO productDTO){
        Product product = new Product();
        product.setId(product.getId());
        product.setName(productDTO.name());
        product.setProductDetails(productDTO.productDetails());
        product.setCategoryProduct(productDTO.categoryProduct());
        product.setPriceProduct(productDTO.priceProduct());
        product.setQuant(productDTO.quant());
        productRepository.save(product);
    }

    public List<Product> showAllProducts(){
        return productRepository.findAll();
    }

    public void removeProduct (Long idProduct) {

        if (productRepository.existsById(idProduct)) {
            productRepository.deleteById(idProduct);
        } else {
            throw new RuntimeException("Id do not exists.");
        }

    }
}
