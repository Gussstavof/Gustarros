package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.models.request.ClientRequest;
import com.gusscarros.core.client.exception.CpfExistException;
import com.gusscarros.core.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CpfValidation implements CreateValidation {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void validator(ClientRequest clientRequest) {
        if (clientRepository.existsByCpf(clientRequest.getCpf())){
         throw new CpfExistException("Cpf already exist");
        }
    }
}
