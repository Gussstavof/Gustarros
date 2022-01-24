package com.gusscarros.core.cliente.service;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

 abstract class Validacoes{

     ClienteRepository repository;

     public Validacoes(ClienteRepository repository) {
         this.repository = repository;
     }
 }
