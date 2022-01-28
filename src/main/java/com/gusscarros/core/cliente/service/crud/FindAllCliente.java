package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllCliente extends Validacoes {


    public FindAllCliente(ClienteRepository repository) {
        super(repository);
    }

    public List<Cliente> allCliente(){

        return repository.findByAtividadeTrue();

    }
}
