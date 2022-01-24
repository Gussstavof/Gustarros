package com.gusscarros.core.endereco.service;

import com.gusscarros.core.endereco.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public abstract class ServiceEndereco {

    public Endereco restEndereco(String cep){
        RestTemplate restTemplate = new RestTemplate();
        Endereco endereco = restTemplate.getForObject("http://viacep.com.br/ws/"+cep+"/json/",
                Endereco.class);
        return endereco;
    }
}
