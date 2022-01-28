package com.gusscarros.core.cliente.service;

import ch.qos.logback.core.net.server.Client;
import com.gusscarros.core.cliente.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ServiceIdade {

    public int calculatorAge(Cliente cliente){

        LocalDate localDate  = LocalDate.now();
        LocalDate dataNascimento = cliente.getDataDeNascimento();
        Period period = Period.between(dataNascimento, localDate);

        return period.getYears();
    }
}
