package com.gusscarros.core.client.service;


import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.CpfValidation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class CpfService  implements ConstraintValidator<CpfValidation, String> {


    private ClientRepository repository;


    @Override
    public void initialize(CpfValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        return !repository.existsByCpf(cpf);

    }
}
