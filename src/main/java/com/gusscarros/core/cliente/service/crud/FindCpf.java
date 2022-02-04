package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.exception.ExceptionNotFound;
import com.gusscarros.core.cliente.model.User;
import com.gusscarros.core.cliente.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.IllegalFormatCodePointException;
import java.util.List;

@Service
public class FindCpf extends Validation {

    public FindCpf(UserRepository repository) {
        super(repository);
    }


    public User searchCpf(String cpf){
        if (repository.findByCpf(cpf) == null){
            throw new ExceptionNotFound("");
        }
        return repository.findByCpf(cpf);

    }




}
