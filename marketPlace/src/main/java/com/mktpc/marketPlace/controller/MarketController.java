package com.mktpc.marketPlace.controller;

import com.mktpc.marketPlace.model.Product;
import com.mktpc.marketPlace.model.dtos.ProductDTO;
import com.mktpc.marketPlace.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> testePage (HttpServletRequest request){
        return ResponseEntity.ok("Authenticated user: " + SecurityContextHolder.getContext().getAuthentication().getName());
    }

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

    @PostMapping("/deleteProduct/{idProduct}")
    public ResponseEntity deleteProduct (@PathVariable Long idProduct){
        productService.removeProduct(idProduct);
        return ResponseEntity.ok().build();
    }
}
