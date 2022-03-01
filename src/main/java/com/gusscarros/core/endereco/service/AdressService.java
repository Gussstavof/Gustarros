package com.gusscarros.core.endereco.service;

import com.gusscarros.core.client.validation.AgeValidation;
import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AdressService implements ConstraintValidator<AgeValidation, Adress> {

    @Autowired
    private AdressInfra adressInfra;

    @Override
    public void initialize(AgeValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Adress adress, ConstraintValidatorContext constraintValidatorContext) {
       if (adressInfra.validationAdress(adress) != null){
        return true;
       }
        return false;
    }
}
