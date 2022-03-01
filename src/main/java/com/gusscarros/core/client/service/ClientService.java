package com.gusscarros.core.client.service;

import com.gusscarros.core.client.exception.ExceptionBadRequest;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.endereco.infra.AdressInfra;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository repository;
    private final AdressInfra validationAdress;
    private final SaveValidService saveValidService;

    public Client saveClient(Client client){
        client.setAdress( validationAdress.validationAdress(client.getAdress()));
        if (returnStatusToClient(client) == null){
            return repository.save(client);
        }
        return returnStatusToClient(client);
    }

    public List<Client> allClient(){
        return repository.findByStatusTrue();
    }

    public Client searchCpf(String cpf){
        return findByCpfOrThrowNotFoundException(cpf);
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

    public Client clientUpdateStatus(boolean status, String cpf){
        var client = findByCpfOrThrowNotFoundException(cpf);
        client.setStatus(status);
        return repository.save(client);
    }

    public void clientDelete(String cpf) {
        repository.deleteById(findByCpfOrThrowNotFoundException(cpf).getId());
    }

    private Client findByCpfOrThrowNotFoundException(final String cpf){
        return repository.findByCpf(cpf).orElseThrow(() -> new ExceptionNotFound("CPF not found"));
    }

    private Client returnStatusToClient(final Client client){
        return saveValidService.returnStatus(client);
    }
}
