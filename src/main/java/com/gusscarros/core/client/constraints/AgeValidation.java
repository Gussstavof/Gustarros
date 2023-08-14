package com.gusscarros.core.client.constraints;

import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeValidation implements ConstraintValidator<AgeValidator, LocalDate > {

    private int calculatorAge(LocalDate birthDate){
        LocalDate localDate  = LocalDate.now();
        return Period.
                between(birthDate, localDate)
                .getYears();
    }

    @Override
    public void initialize(AgeValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return calculatorAge(birthDate) >= 18;
    }
}
