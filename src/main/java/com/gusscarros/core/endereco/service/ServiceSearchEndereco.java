package com.gusscarros.core.endereco.service;

import com.gusscarros.core.endereco.model.Endereco;

public class ServiceSearchEndereco extends ServiceEndereco{

    public Endereco findEndereco(String cep){
        return restEndereco(cep);

    }
}
