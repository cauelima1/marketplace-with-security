package com.mktpc.marketPlace.repository;

import com.mktpc.marketPlace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public void deleteById(Long productId);
}
