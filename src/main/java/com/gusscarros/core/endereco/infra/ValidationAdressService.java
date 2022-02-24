package com.gusscarros.core.endereco.infra;

import com.gusscarros.core.endereco.model.Adress;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ValidationAdressService  {

    public Adress validationAdress(Adress adress){
        String number = adress.getNumero();
        adress = restEndereco(adress.getCep());
        if (adress.getLogradouro() == null){
            return null;
        }
        adress.setNumero(number);
        adress.setCep(adress.getCep().replace("-", ""));
        return adress;
    }


     private Adress restEndereco(String cep){
        RestTemplate restTemplate = new RestTemplate();
         return restTemplate.getForObject("http://viacep.com.br/ws/"+cep+"/json/",
                 Adress.class);
    }
}


