package com.mktpc.marketPlace.repository;

import com.mktpc.marketPlace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getOrderById (Long id);

    Order findByClientName (String name);

    boolean existsByClientName(String name);

}
