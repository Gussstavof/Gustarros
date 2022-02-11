package com.gusscarros.core.endereco.service;

import com.gusscarros.core.endereco.infra.AdressService;
import com.gusscarros.core.endereco.model.Adress;
import org.springframework.stereotype.Service;


@Service
public class ValidationAdressService extends AdressService {


    public Adress validationAdress(Adress adress){
        String number = adress.getNumero();
        adress = restEndereco(adress.getCep());
        if (adress.getLogradouro() == null){
            return null;
        }
        adress.setNumero(number);
        return adress;
    }



}
