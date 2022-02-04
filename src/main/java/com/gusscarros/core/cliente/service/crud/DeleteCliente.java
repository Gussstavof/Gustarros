package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;


@Service
public class DeleteCliente extends Validacoes {

    public DeleteCliente(ClienteRepository repository) {
        super(repository);
    }

    public Cliente clienteDelete(Cliente newCliente, String id){
        return repository.findById(id).map(cliente -> {
            cliente.setAtividade(newCliente.isAtividade());
            return repository.save(cliente);

        }).orElseGet(() -> {
            newCliente.setId(id);
            return repository.save(newCliente);
        });
    }
}
