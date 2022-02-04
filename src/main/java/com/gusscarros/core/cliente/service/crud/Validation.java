package com.gusscarros.core.cliente.service.crud;

import com.gusscarros.core.cliente.repository.UserRepository;

 abstract class Validation {

     UserRepository repository;

     public Validation(UserRepository repository) {
         this.repository = repository;
     }


 }
