package com.gusscarros.core.client.service;

import com.gusscarros.core.client.exception.ExceptionBadRequest;
import com.gusscarros.core.client.exception.ExceptionUnauthorized;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SaveValidService {

    private final ClientRepository repository;

    private boolean checkCpf(Client client){
        return repository.existsByCpf(client.getCpf());
    }

    private boolean checkAdress(Client client){
        return client.getAdress() != null;
    }

    public Client returnStatus(Client client){
        if (checkCpf(client)){
            throw new ExceptionBadRequest("Already used CPF");
        }
        else if ((!checkAdress(client))){
            throw new ExceptionBadRequest("Invalid Adress");
        }
        return null;
    }

}
