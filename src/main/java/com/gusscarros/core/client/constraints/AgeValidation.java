package com.gusscarros.core.client.constraints;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeValidation implements ConstraintValidator<AgeValidator, LocalDate > {


    private int calculatorAge(LocalDate birthDate){
        LocalDate localDate  = LocalDate.now();
        Period period = Period.between(birthDate, localDate);
        return period.getYears();

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
