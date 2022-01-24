package com.gusscarros.core.cliente.service;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCliente extends Validacoes{

    public UpdateCliente(ClienteRepository repository) {
        super(repository);
    }

    public Cliente clienteUpdate(Cliente newCliente, String id){

        return repository.findById(id).map(cliente -> {
            cliente.setCartao(newCliente.getCartao());
            cliente.setEndereco(newCliente.getEndereco());
            cliente.setNome(newCliente.getNome());
            return repository.save(cliente);

        }).orElseGet(() -> {
            newCliente.setId(id);
            return repository.save(newCliente);
        });

    }
}
