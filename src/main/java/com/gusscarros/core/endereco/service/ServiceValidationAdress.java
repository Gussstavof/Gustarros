package com.gusscarros.core.endereco.service;

import com.gusscarros.core.endereco.model.Adress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceValidationAdress {

    @Autowired
    ServiceSearchAdress searchAdress;

    public Adress validationAdress(Adress adress){
        String number = adress.getNumero();
        adress = searchAdress.restEndereco(adress.getCep());
        if (adress.getLogradouro() == null){
            return null;
        }
        adress.setNumero(number);
        return adress;
    }

}
