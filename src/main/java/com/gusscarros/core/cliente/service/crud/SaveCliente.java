package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import com.gusscarros.core.cliente.service.ServiceIdade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveCliente extends Validacoes {

    @Autowired
    ServiceIdade serviceIdade;

    public SaveCliente(ClienteRepository repository, ServiceIdade serviceIdade) {
        super(repository);
        this.serviceIdade = serviceIdade;
    }


    public Cliente saveCliente(Cliente cliente){
        if (serviceIdade.calculatorAge(cliente) >= 18){
            return repository.save(cliente);
        }
       return null;
    }
}
