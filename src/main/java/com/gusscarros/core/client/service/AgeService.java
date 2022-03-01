package com.gusscarros.core.client.service;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.validation.AgeValidation;
import org.springframework.stereotype.Service;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

@Service
public class AgeService implements ConstraintValidator<AgeValidation, LocalDate > {


    private int calculatorAge(LocalDate birthDate){
        LocalDate localDate  = LocalDate.now();
        LocalDate dataNascimento = birthDate;
        Period period = Period.between(dataNascimento, localDate);
        return period.getYears();

    }

    @Override
    public void initialize(AgeValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (calculatorAge(birthDate) < 18){
            return false;
        }
        return true;
    }
}
