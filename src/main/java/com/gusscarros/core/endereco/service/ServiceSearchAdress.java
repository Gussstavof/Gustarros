package com.gusscarros.core.endereco.service;

import com.gusscarros.core.endereco.infra.ServiceAdress;
import com.gusscarros.core.endereco.model.Adress;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceSearchAdress extends ServiceAdress {

    public Adress findEndereco(String cep){
        return restEndereco(cep);
    }

}
