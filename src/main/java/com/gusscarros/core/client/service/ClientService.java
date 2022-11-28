package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.*;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.entity.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.CreateValidation;
import com.gusscarros.core.address.infra.AddressInfra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AddressInfra addressInfra;

    @Autowired
    private Mapper mapper;

    @Autowired
    List<CreateValidation> createValidations;

    public ClientDto saveClient(ClientDto clientDto){
        createValidations.forEach(createValidation -> createValidation.validator(clientDto));
        return mapper.toClientDto(repository.save(mapper.toClient(clientDto)));
    }

    public List<ClientDto> allClient(){
        return mapper.convertListDto(repository.findByStatusTrue());
    }

    public ClientDto searchCpf(String cpf){
        return mapper.toClientDto(findByCpfOrThrowNotFoundException(cpf));
    }

    public List<ClientDto> searchName(String name){
        var clients = repository.findByNameContains(name);
        if (clients.isEmpty()){
            throw new ExceptionNotFound("Name not found");
        }
        return mapper.convertListDto(clients);
    }

    public ClientDto clientUpdate(ClientDto clientDto, String cpf){

        return repository.findByCpf(cpf).map(client -> {
            client.setCreditCard(clientDto.getCreditCard());
            client.setAddress(addressInfra.validationAdress(clientDto.getAddress()));
            client.setName(clientDto.getName());
            repository.save(client);
            return mapper.toClientDto(client);
        }).orElseThrow(() -> new ExceptionNotFound("Cpf not found"));


    }

    public ClientPatchDto clientUpdateStatus(boolean status, String cpf){
        var client = findByCpfOrThrowNotFoundException(cpf);
        client.setStatus(status);
        repository.save(client);
        return mapper.cpfAndStatus(client);
    }

    public void clientDelete(String cpf) {
        repository.deleteById(findByCpfOrThrowNotFoundException(cpf).getId());
    }

    private Client findByCpfOrThrowNotFoundException(final String cpf){
        return repository.findByCpf(cpf).orElseThrow(() -> new ExceptionNotFound("CPF not found"));
    }

}
