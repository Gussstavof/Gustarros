package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.ClientGetDto;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.dto.ClientPutDto;
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
    private final AdressInfra adressInfra;


    public ClientPostDto saveClient(ClientPostDto clientPostDto){

        //clientPostDto.setAdress(adressInfra.validationAdress(clientPostDto.getAdress()));
        Client client = repository.save(clientPostDto.build());
        repository.save(client);
        return clientPostDto;

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
        return repository.findById(findByCpfOrThrowNotFoundException(cpf).getId()).map(client -> {
            client.setCreditCard(newClient.getCreditCard());
            client.setAdress(adressInfra.validationAdress(newClient.getAdress()));
            client.setName(newClient.getName());
            repository.save(client);
            return new ClientPutDto(client);
        }).orElseThrow(() -> new ExceptionBadRequest("Client doesn't exist"));
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
