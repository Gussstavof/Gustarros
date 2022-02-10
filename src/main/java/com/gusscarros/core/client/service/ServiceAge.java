package com.gusscarros.core.client.service;

import com.gusscarros.core.client.model.Client;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ServiceAge {

    public int calculatorAge(Client client){
        LocalDate localDate  = LocalDate.now();
        LocalDate dataNascimento = client.getBirthDate();
        Period period = Period.between(dataNascimento, localDate);
        return period.getYears();
    }


}
