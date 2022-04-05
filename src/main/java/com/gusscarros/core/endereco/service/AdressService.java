package com.gusscarros.core.endereco.service;

import com.gusscarros.core.client.validation.AgeValidation;
import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import com.gusscarros.core.endereco.validation.AdressValidation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Service
public class AdressService implements ConstraintValidator<AdressValidation, Adress> {

    @Autowired
    private  AdressInfra adressInfra;

    public AdressService(){}

    @Override
    public void initialize(AdressValidation constraintAnnotation) {
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
