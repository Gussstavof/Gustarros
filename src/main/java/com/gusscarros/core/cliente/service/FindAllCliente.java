package com.gusscarros.core.cliente.service;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
