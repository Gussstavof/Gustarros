package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.exception.ExceptionBadRequest;
import com.gusscarros.core.cliente.model.User;
import com.gusscarros.core.cliente.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class DeleteUser extends Validation {

    public DeleteUser(UserRepository repository) {
        super(repository);
    }

    public User clienteDelete(User newUser, String id){
        return repository.findById(id).map(cliente -> {
            cliente.setStatus(newUser.isStatus());
            return repository.save(cliente);

        }).orElseThrow(() -> new ExceptionBadRequest("User doesn't exist"));
    }
}
