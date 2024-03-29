package com.gusscarros.core.client.repository;

import com.gusscarros.core.client.models.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    List<Client> findByNameContains(String name);
    Optional<Client> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Page<Client> findByStatusTrue(Pageable pageable);

}
