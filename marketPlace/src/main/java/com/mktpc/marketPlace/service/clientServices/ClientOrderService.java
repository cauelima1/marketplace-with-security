package com.mktpc.marketPlace.service.clientServices;

import com.mktpc.marketPlace.model.Client;
import com.mktpc.marketPlace.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOrderService {

    @Autowired
    private ClientRepository clientRepository;

    public void depositToClient(Double value){
        if(value >= 0) {
            Client client = clientRepository.findByName(getLogin());
            client.setBalance(client.getBalance() + value);
            clientRepository.save(client);
        } else {
            throw new RuntimeException("Invalid deposit value.");
        }
    }


    public void firstLogin() {
        Client client = new Client(getLogin(), 0.0, null);
        clientRepository.save(client);
    }

    public List<Client> getClients (){
        return clientRepository.findAll();
    }

    public String getLogin () {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
