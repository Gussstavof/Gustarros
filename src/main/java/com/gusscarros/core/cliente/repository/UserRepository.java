package com.gusscarros.core.cliente.repository;

import com.gusscarros.core.cliente.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByNameContains(String name);

    User findByCpf(String cpf);

    List<User> findByStatusTrue();
}
