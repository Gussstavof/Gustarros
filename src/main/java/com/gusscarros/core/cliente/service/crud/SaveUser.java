package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.exception.ExceptionBadRequest;
import com.gusscarros.core.cliente.model.User;
import com.gusscarros.core.cliente.repository.UserRepository;
import com.gusscarros.core.cliente.service.ServiceAge;
import com.gusscarros.core.endereco.service.ServiceValidationAdress;
import org.springframework.stereotype.Service;

@Service
public class SaveUser extends Validation {


     ServiceAge serviceAge;
     ServiceValidationAdress validationAdress;


    public SaveUser(UserRepository repository, ServiceAge serviceAge,
                    ServiceValidationAdress validationAdress) {
        super(repository);
        this.serviceAge = serviceAge;
        this.validationAdress = validationAdress;
    }


    public User saveCliente(User user){
         user.setAdress( validationAdress.validationAdress(user.getAdress()));
        if (serviceAge.calculatorAge(user) >= 18){
            if (user.getAdress() != null) {
                return repository.save(user);
            }

            throw new ExceptionBadRequest("Endereço Inválido");
        }

        throw  new  ExceptionBadRequest("Idade Inválida");

    }

}

