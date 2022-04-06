package com.gusscarros.core.client.validation;


import com.gusscarros.core.client.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Service
public class CpfValidation implements ConstraintValidator<CpfValidator, String> {

    @Autowired
    public   ClientRepository repository;

    @Override
    public void initialize(CpfValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        return !repository.existsByCpf(cpf);

    }
}
