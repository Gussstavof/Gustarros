package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class FindCpf extends Validacoes {

    public FindCpf(ClienteRepository repository) {
        super(repository);
    }

    public Cliente searchCpf(String cpf){
       return repository.findByCpfContains(cpf);

    }


}
