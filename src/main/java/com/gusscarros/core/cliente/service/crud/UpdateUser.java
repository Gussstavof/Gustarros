package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.exception.ExceptionBadRequest;
import com.gusscarros.core.cliente.model.User;
import com.gusscarros.core.cliente.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUser extends Validation {

    public UpdateUser(UserRepository repository) {
        super(repository);
    }

    public User clienteUpdate(User newUser, String id){

        return repository.findById(id).map(cliente -> {
            cliente.setCreditCard(newUser.getCreditCard());
            cliente.setAdress(newUser.getAdress()); // COM ERRO!!!!
            cliente.setName(newUser.getName());
            return repository.save(cliente);

        }).orElseGet(() -> {
            newUser.setId(id);
            return repository.save(newUser);
        });
    }
}
