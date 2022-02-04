package com.gusscarros.core.endereco.infra;

import com.gusscarros.core.endereco.model.Adress;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public abstract class ServiceAdress {

    public Adress restEndereco(String cep){
        RestTemplate restTemplate = new RestTemplate();
        Adress adress = restTemplate.getForObject("http://viacep.com.br/ws/"+cep+"/json/",
                Adress.class);
        return adress;
    }
}
