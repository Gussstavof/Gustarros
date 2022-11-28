package com.gusscarros.core.address.infra;

import com.gusscarros.core.address.entity.Address;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AddressInfra {

    public Address validationAdress(Address address){
        String number = address.getNumero();
        address = restEndereco(address.getCep());
        if (address.getLogradouro() == null){
            return null;
        }
        address.setNumero(number);
        address.setCep(address.getCep().replace("-", ""));
        return address;
    }


     private Address restEndereco(final String cep){
        RestTemplate restTemplate = new RestTemplate();
         return restTemplate.getForObject("http://viacep.com.br/ws/"+cep+"/json/",
                 Address.class);
    }
}



