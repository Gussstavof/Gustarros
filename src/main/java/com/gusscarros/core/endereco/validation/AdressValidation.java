package com.gusscarros.core.endereco.validation;

import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class AdressValidation implements ConstraintValidator<AdressValidator, Adress> {

    @Autowired
    private  AdressInfra adressInfra;

    public AdressValidation(){}

    @Override
    public void initialize(AdressValidator constraintAnnotation) {
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
