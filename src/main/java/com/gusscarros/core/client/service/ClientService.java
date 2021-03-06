package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.*;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.endereco.infra.AdressInfra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AdressInfra adressInfra;

    @Autowired
    private Mapper mapper;


    public ClientPostDto saveClient(ClientPostDto clientPostDto){
        Client client = mapper.toClient(clientPostDto);

        repository.save(client);

        return mapper.toClientPostDto(client);
    }

    public List<ClientGetDto> allClient(){
        var client = repository.findByStatusTrue();
        return ClientGetDto.convertListDto(client);
    }

    public ClientGetDto searchCpf(String cpf){
        var client = findByCpfOrThrowNotFoundException(cpf);
        return new ClientGetDto(client);
    }

    public List<ClientGetDto> searchName(String name){
        var clients = repository.findByNameContains(name);
        if (clients.isEmpty()){
            throw new ExceptionNotFound("Name not found");
        }
        return ClientGetDto.convertListDto(clients);
    }

    public ClientPutDto clientUpdate(ClientPutDto newClient, String cpf){
        newClient.build();
        return repository.findByCpf(cpf).map(client -> {
            client.setCreditCard(newClient.getCreditCard());
            client.setAdress(adressInfra.validationAdress(newClient.getAdress()));
            client.setName(newClient.getName());
            repository.save(client);
            return new ClientPutDto(client);
        }).orElseThrow(() -> new ExceptionNotFound("Cpf not found"));
    }

    public ClientPatchDto clientUpdateStatus(boolean status, String cpf){
        var client = findByCpfOrThrowNotFoundException(cpf);
        client.setStatus(status);
        repository.save(client);
        return new ClientPatchDto(client);
    }

    public void clientDelete(String cpf) {
        repository.deleteById(findByCpfOrThrowNotFoundException(cpf).getId());
    }

    private Client findByCpfOrThrowNotFoundException(final String cpf){
        return repository.findByCpf(cpf).orElseThrow(() -> new ExceptionNotFound("CPF not found"));
    }

}
