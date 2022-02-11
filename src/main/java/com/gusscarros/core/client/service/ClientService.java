package com.gusscarros.core.client.service;

import com.gusscarros.core.client.exception.ExceptionBadRequest;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.endereco.service.ValidationAdressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository repository;
    private final ValidationAdressService validationAdress;
    private final SaveValidService saveValidService;

    public Client saveClient(Client client){
        client.setAdress( validationAdress.validationAdress(client.getAdress()));

        return saveValidService.checkAllClient(client);
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
        }).orElseThrow(() -> new ExceptionBadRequest("Client doesn't exist"));
    }

    public Client clientDelete(Client newClient, String cpf){
        return repository.findByCpf(cpf).map(client -> {
            client.setStatus(newClient.isStatus());
            return repository.save(client);
        }).orElseThrow(() -> new ExceptionBadRequest("Client doesn't exist"));
    }

}
