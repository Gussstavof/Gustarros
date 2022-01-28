package com.gusscarros.core.endereco.service;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import com.gusscarros.core.endereco.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

public class ServiceSearchEndereco extends ServiceEndereco{


    public Endereco findEndereco(Endereco endereco){
        String cep = endereco.getCep();
        return restEndereco(cep);
    }
}
