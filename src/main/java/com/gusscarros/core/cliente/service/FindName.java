package com.gusscarros.core.cliente.service;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindName extends Validacoes {

    public FindName(ClienteRepository repository) {
        super(repository);
    }

    public List<Cliente> searchName(String nome){
        return repository.findByNomeContains(nome);
    }
}
