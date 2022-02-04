package com.gusscarros.core.cliente.service;

import com.gusscarros.core.cliente.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ServiceAge {

    public int calculatorAge(User user){

        LocalDate localDate  = LocalDate.now();
        LocalDate dataNascimento = user.getBirthDate();
        Period period = Period.between(dataNascimento, localDate);

        return period.getYears();
    }
}
