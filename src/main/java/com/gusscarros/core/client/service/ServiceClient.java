package com.gusscarros.core.client.service;

import com.gusscarros.core.client.exception.ExceptionBadRequest;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.endereco.service.ServiceValidationAdress;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceClient {

    ClientRepository repository;
    ServiceValidationAdress validationAdress;
    ServiceAge serviceAge;

    public ServiceClient(ClientRepository repository, ServiceValidationAdress validationAdress
            , ServiceAge serviceAge) {
        this.repository = repository;
        this.validationAdress = validationAdress;
        this.serviceAge = serviceAge;

    }

    public Client saveClient(Client client){
        client.setAdress( validationAdress.validationAdress(client.getAdress()));
        if (serviceAge.calculatorAge(client) >= 18){
            if (client.getAdress() != null) {
                return repository.save(client);
            }
            throw new ExceptionBadRequest("Endereço Inválido");
        }
        throw  new  ExceptionBadRequest("Idade Inválida");
    }

    public List<Client> allClient(){
        return repository.findByStatusTrue();
    }

    public Optional<Client> searchCpf(String cpf){
        if (!repository.findByCpf(cpf).isPresent()){
            throw new ExceptionNotFound("CPF not found");
        }
        return repository.findByCpf(cpf);
    }

    public List<Client> searchName(String name){
        if (repository.findByNameContains(name).isEmpty()){
            throw new ExceptionNotFound("Name not found");
        }
        return repository.findByNameContains(name);
    }

    public Client clientUpdate(Client newClient, String cpf){
        return repository.findByCpf(cpf).map(client -> {
            client.setCreditCard(newClient.getCreditCard());
            client.setAdress(validationAdress.validationAdress(newClient.getAdress()));
            client.setName(newClient.getName());
            return repository.save(client);
        }).orElseThrow(() -> new ExceptionBadRequest("CPF doesn't exist"));
    }

    public Client clientDelete(Client newClient, String cpf){
        return repository.findByCpf(cpf).map(client -> {
            client.setStatus(newClient.isStatus());
            return repository.save(client);
        }).orElseThrow(() -> new ExceptionBadRequest("Client doesn't exist"));
    }

}
