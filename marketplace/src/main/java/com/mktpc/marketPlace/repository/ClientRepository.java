package com.mktpc.marketPlace.repository;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Order> findOrderByName(String name);

    boolean existsByName (String name);

    Client findByName(String name);

}
