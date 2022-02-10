package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.service.ServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ClientController {

     ServiceClient serviceClient;

    public ClientController(ServiceClient serviceClient) {
      this.serviceClient = serviceClient;
    }

    @PostMapping("/save")
    public ResponseEntity<Client> save(@Valid @RequestBody Client client){
         return ResponseEntity.ok(serviceClient.saveClient(client));
    }

    @GetMapping("/all")
    public List<Client> getAll(){
        return serviceClient.allClient();
    }

    @GetMapping("/searchname")
    public List<Client> findByName(@RequestParam("name") String name){

        return serviceClient.searchName(name);

    }

    @GetMapping("/searchcpf")
    public Optional<Client> findByCpf(@RequestParam("cpf") String cpf){

        return serviceClient.searchCpf(cpf);

    }

    @PutMapping("/update/{cpf}")
    public ResponseEntity<Client> update(@RequestBody Client newClient, @PathVariable String cpf){

        return ResponseEntity.ok(serviceClient.clientUpdate(newClient, cpf));
    }

    @PutMapping("/delete/{cpf}")
    public ResponseEntity<Client> delete(@RequestBody Client newClient, @PathVariable String cpf){

        return ResponseEntity.ok(serviceClient.clientDelete(newClient, cpf));

    }

}
