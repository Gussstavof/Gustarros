package com.gusscarros.core.client.service;

import com.gusscarros.core.client.exception.ExceptionBadRequest;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.endereco.service.ValidationAdressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SaveValidService {

    private final ClientRepository repository;
    private final ValidationAdressService validationAdress;
    private final AgeService ageService;


    public boolean checkAge(Client client){
        return ageService.calculatorAge(client) >= 18?true:false;
    }

    public boolean checkCpf(Client client){
        return (repository.findByCpf(client.getCpf()).isPresent())?false:true;
    }

    public boolean checkAdress(Client client){
        return client.getAdress() != null?true:false;
    }

    public Client checkAllClient(Client client){
        if (checkCpf(client) && checkAge(client) && checkAdress(client)){
            return repository.save(client);
        }
        else if (!checkAge(client)){
            throw  new  ExceptionBadRequest("Invalid age");
        }
        else if (!checkCpf(client)){
            throw new ExceptionBadRequest("Already used CPF");
        }
        else if ((!checkAdress(client))){
            throw new ExceptionBadRequest("Invalid Adress");
        }
        else throw new ExceptionBadRequest("Erro");
    }
/*
    public Client checkClient(Client client){
        client.setAdress( validationAdress.validationAdress(client.getAdress()));
        if (ageService.calculatorAge(client) >= 18){
            if (client.getAdress() != null) {
                return repository.save(client);
            }
            throw new ExceptionBadRequest("Endereço Inválido");
        }
        throw  new  ExceptionBadRequest("Idade Inválida");
    }

 */
}
