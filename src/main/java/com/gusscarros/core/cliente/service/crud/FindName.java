package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.exception.ExceptionNotFound;
import com.gusscarros.core.cliente.model.User;
import com.gusscarros.core.cliente.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class FindName extends Validation {

    public FindName(UserRepository repository) {
        super(repository);
    }

    public List<User> searchName(String name){

        if (repository.findByNameContains(name).isEmpty()){
            throw new ExceptionNotFound("Name not found");
        }
        return repository.findByNameContains(name);

    }
}
