package com.mktpc.marketPlace.repository;

import com.mktpc.marketPlace.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByName (String name);

    Client findByName(String name);

}
