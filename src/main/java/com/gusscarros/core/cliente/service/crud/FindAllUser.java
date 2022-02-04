package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.model.User;
import com.gusscarros.core.cliente.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllUser extends Validation {


    public FindAllUser(UserRepository repository) {
        super(repository);
    }

    public List<User> allCliente(){

        return repository.findByStatusTrue();

    }
}
