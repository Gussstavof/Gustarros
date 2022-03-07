package com.gusscarros.core.client.service;


import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.CpfValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
@Service
public class CpfService  implements ConstraintValidator<CpfValidation, String> {

    private final ClientRepository repository;

    @Override
    public void initialize(CpfValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        if (repository.existsByCpf(cpf)){
            return false;
        }
        return true;
    }
}
