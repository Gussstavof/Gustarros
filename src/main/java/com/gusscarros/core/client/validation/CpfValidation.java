package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.dto.ClientDto;
import com.gusscarros.core.client.exception.CpfExistException;
import com.gusscarros.core.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CpfValidation implements CreateValidation {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void validator(ClientDto clientDto) {
        if (clientRepository.existsByCpf(clientDto.getCpf())){
         throw new CpfExistException("Cpf already exist");
        }
    }
}
