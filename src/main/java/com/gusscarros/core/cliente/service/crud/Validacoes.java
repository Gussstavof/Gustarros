package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.repository.ClienteRepository;

 abstract class Validacoes{

     ClienteRepository repository;

     public Validacoes(ClienteRepository repository) {
         this.repository = repository;
     }

     protected Validacoes() {
     }
 }
