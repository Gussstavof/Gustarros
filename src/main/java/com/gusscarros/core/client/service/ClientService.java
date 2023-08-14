package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.*;
import com.gusscarros.core.client.dto.request.ClientRequest;
import com.gusscarros.core.client.dto.response.ClientResponse;
import com.gusscarros.core.client.exception.NotFoundException;
import com.gusscarros.core.client.entity.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.CreateValidation;
import com.gusscarros.core.address.infra.AddressInfra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public String saveClient(ClientRequest clientRequest){
        createValidations.forEach(
                createValidation -> createValidation.validator(clientRequest)
        );

        Client client = repository.save(clientRequest.toClient());

        return new ClientResponse(client).getCpf();
    }

    public Page<ClientResponse> allClient(Pageable pageable){
        return repository
                .findByStatusTrue(pageable)
                .map(ClientResponse::new);
    }

    public ClientResponse searchCpf(String cpf){
        return new ClientResponse(findByCpfOrThrowNotFoundException(cpf));
    }

    public List<ClientResponse> searchName(String name){
        var clients = repository.findByNameContains(name);
        if (clients.isEmpty()){
            throw new NotFoundException("Name not found");
        }
        return clients.stream().map(ClientResponse::new).toList();
    }

    public ClientDto clientUpdate(ClientDto clientDto, String cpf){
        return repository.findByCpf(cpf).map(client -> {
            client.setCreditCard(clientDto.getCreditCard());
            client.setAddress(addressInfra.validationAdress(clientDto.getAddress()));
            client.setName(clientDto.getName());
            repository.save(client);
            return mapper.toClientDto(client);
        }).orElseThrow(() -> new NotFoundException("Cpf not found"));
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
        return repository.findByCpf(cpf).orElseThrow(() -> new NotFoundException("CPF not found"));
    }

}
