package com.mktpc.marketPlace.repository;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public Order findByClientName (String name);

    public boolean existsByClientName(String name);

}
