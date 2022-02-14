package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/usuarios")
public class ClientController {

    @Autowired
    private ClientService clientService;



    @PostMapping("/save")
    public ResponseEntity<Client> save(@Valid @RequestBody Client client){
         return ResponseEntity.ok(clientService.saveClient(client));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAll(){
        return ResponseEntity.ok(clientService.allClient());
    }

    @GetMapping("/searchname")
    public ResponseEntity<List<Client> >findByName(@RequestParam("name") String name){

        return ResponseEntity.ok(clientService.searchName(name));

    }

    @GetMapping("/searchcpf")
    public ResponseEntity<Optional<Client>> findByCpf(@RequestParam("cpf") String cpf){

        return ResponseEntity.ok(clientService.searchCpf(cpf));

    }

    @PutMapping("/update/{cpf}")
    public ResponseEntity<Client> update(@RequestBody Client newClient, @PathVariable String cpf){

        return ResponseEntity.ok(clientService.clientUpdate(newClient, cpf));
    }

    @PutMapping("/delete/{cpf}")
    public ResponseEntity<Client> delete(@RequestBody Client newClient, @PathVariable String cpf){

        return ResponseEntity.ok(clientService.clientDelete(newClient, cpf));

    }

}
