package com.mktpc.marketPlace.service;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createAndDeposite(Double value){
        if (clientRepository.existsByName(getLogin()) && value > 0) {
            Client client = clientRepository.findByName(getLogin());
            client.setBalance(client.getBalance() + value);
            clientRepository.save(client);
            return client;
        } if (!clientRepository.existsByName(getLogin())){
            Client client = new Client(getLogin(), value, null);
            clientRepository.save(client);
            return client;
        }
        return null;
    }

    public List<Client> getClients (){
        return clientRepository.findAll();
    }

    public String getLogin () {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
