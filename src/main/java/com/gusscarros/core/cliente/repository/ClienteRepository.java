package com.gusscarros.core.cliente.repository;

import com.gusscarros.core.cliente.model.Cliente;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

    List<Cliente> findByNomeContains(String nome);

    Cliente findByCpfContains(String cpf);

    List<Cliente> findByAtividadeTrue();
}
