package com.gusscarros.core.cliente.service;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveCliente extends Validacoes {


    public SaveCliente(ClienteRepository repository) {
        super(repository);
    }


    public Cliente saveCliente(Cliente cliente){
        return repository.save(cliente);
    }
}
