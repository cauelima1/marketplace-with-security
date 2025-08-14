package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.ProductDTO;
import com.mktpc.marketPlace.model.dtos.QuantDeleteDTO;
import com.mktpc.marketPlace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

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

    public void removeQuant (Long idProduct, QuantDeleteDTO quantDeleteDTO){
        if (productRepository.existsById(idProduct)){
            Product product = productRepository.findById(idProduct).get();
            if(product.getQuant()>=quantDeleteDTO.quant() && quantDeleteDTO.quant() > 0){
                Long balance = product.getQuant() - quantDeleteDTO.quant();
                product.setQuant(balance);
                productRepository.save(product);
            } else {
                throw new RuntimeException("insufficient quantity");
            }
        } else {
            throw new RuntimeException("Id do not exists.");
        }
    }
}
