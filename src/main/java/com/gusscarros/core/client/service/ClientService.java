package com.gusscarros.core.client.service;

import com.gusscarros.core.client.models.request.ClientRequest;
import com.gusscarros.core.client.models.response.ClientResponse;
import com.gusscarros.core.client.exception.NotFoundException;
import com.gusscarros.core.client.models.entity.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.Validation;
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
    List<Validation> validations;

    public String save(ClientRequest clientRequest) {
        check(clientRequest);

        Client client = repository.save(clientRequest.toClient());

        return new ClientResponse(client).getCpf();
    }

    public Page<ClientResponse> getAll(Pageable pageable) {
        return repository
                .findByStatusTrue(pageable)
                .map(ClientResponse::new);
    }

    public ClientResponse searchByCpf(String cpf) {
        return new ClientResponse(findByCpfOrThrowNotFoundException(cpf));
    }

    public List<ClientResponse> searchByName(String name) {
        var clients = repository.findByNameContains(name);
        if (clients.isEmpty()) {
            throw new NotFoundException("Name not found");
        }
        return clients.stream().map(ClientResponse::new).toList();
    }

    public ClientResponse update(ClientRequest clientRequest, String cpf) {

        check(clientRequest);

        return repository.findByCpf(cpf).map(client -> {
            client.setCreditCard(clientRequest.getCreditCard());
            client.setAddress(clientRequest.getAddress());
            client.setName(clientRequest.getName());
            repository.save(client);
            return new ClientResponse(client);
        }).orElseThrow(() -> new NotFoundException("Cpf not found"));
    }

    public ClientResponse updateStatus(boolean status, String cpf) {
        Client client = findByCpfOrThrowNotFoundException(cpf);
        client.setStatus(status);
        repository.save(client);
        return new ClientResponse(client);
    }

    public void deleteByCpf(String cpf) {
        repository.deleteById(findByCpfOrThrowNotFoundException(cpf).getId());
    }

    private Client findByCpfOrThrowNotFoundException(final String cpf) {
        return repository.findByCpf(cpf).orElseThrow(() -> new NotFoundException("CPF not found"));
    }

    private void check(ClientRequest request) {
        validations.forEach(
                validation -> validation.validator(request)
        );
    }

}
