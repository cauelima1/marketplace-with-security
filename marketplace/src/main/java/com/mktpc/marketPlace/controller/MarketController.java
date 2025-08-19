package com.mktpc.marketPlace.controller;

import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.dtosRequest.ProductDTO;
import com.mktpc.marketPlace.model.dtos.dtosRequest.QuantDeleteDTO;
import com.mktpc.marketPlace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private ProductService productService;



    @PostMapping("/addProduct")
    public String createProduct (@RequestBody ProductDTO productDTO){
        productService.addProduct(productDTO);
        return ResponseEntity.ok(productDTO).toString();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> showProducts(){
        List<Product> products = productService.showAllProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/deleteProduct/{idProduct}")
    public ResponseEntity<Product> deleteProduct (@PathVariable Long idProduct){
        productService.removeProduct(idProduct);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeQuantity/{idProduct}")
    public ResponseEntity<Product> deleteQuantProd (@PathVariable Long idProduct, @RequestBody QuantDeleteDTO quant){
        try{
            productService.removeQuant(idProduct, quant);
            return ResponseEntity.ok().build();
        }   catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
