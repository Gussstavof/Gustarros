package com.gusscarros.core.client.service;

import com.gusscarros.core.client.models.request.ClientRequest;
import com.gusscarros.core.client.models.response.ClientResponse;
import com.gusscarros.core.client.exception.NotFoundException;
import com.gusscarros.core.client.models.entity.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.CreateValidation;
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
    List<CreateValidation> createValidations;

    public String saveClient(ClientRequest clientRequest){
        check(clientRequest);

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

    public ClientResponse clientUpdate(ClientRequest clientRequest, String cpf){

        check(clientRequest);

        return repository.findByCpf(cpf).map(client -> {
            client.setCreditCard(clientRequest.getCreditCard());
            client.setAddress(clientRequest.getAddress());
            client.setName(clientRequest.getName());
            repository.save(client);
            return new ClientResponse(client);
        }).orElseThrow(() -> new NotFoundException("Cpf not found"));
    }

    public ClientResponse clientUpdateStatus(boolean status, String cpf){
        Client client = findByCpfOrThrowNotFoundException(cpf);
        client.setStatus(status);
        repository.save(client);
        return new ClientResponse(client);
    }

    public void clientDelete(String cpf) {
        repository.deleteById(findByCpfOrThrowNotFoundException(cpf).getId());
    }

    private Client findByCpfOrThrowNotFoundException(final String cpf){
        return repository.findByCpf(cpf).orElseThrow(() -> new NotFoundException("CPF not found"));
    }

    private void check(ClientRequest request){
        createValidations.forEach(
                createValidation -> createValidation.validator(request)
        );
    }

}
