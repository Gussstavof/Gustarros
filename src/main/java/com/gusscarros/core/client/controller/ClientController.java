package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.dto.ClientGetDto;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.dto.ClientPutDto;
import com.gusscarros.core.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    
    @PostMapping
    public ResponseEntity<ClientPostDto> save(@Valid @RequestBody ClientPostDto clientPostDto,
                                              URI location){
        return ResponseEntity.created(location).body(clientService.saveClient(clientPostDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientGetDto>> getAll(){
        return ResponseEntity.ok(clientService.allClient());
    }

    @GetMapping("/searchname")
    public ResponseEntity<List<ClientGetDto> >findByName(@RequestParam("name") String name){
        return ResponseEntity.ok(clientService.searchName(name));
    }

    @GetMapping("/searchcpf")
    public ResponseEntity<ClientGetDto> findByCpf(@RequestParam("cpf") String cpf){
        return ResponseEntity.ok(clientService.searchCpf(cpf));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClientPutDto> update(@Valid @RequestBody ClientPutDto newClient,
                                               @PathVariable String cpf
                                               ,URI uri){
        return ResponseEntity.created(uri).body(clientService.clientUpdate(newClient.build(),cpf));
    }

    @PatchMapping("/{cpf}/{status}")
    public ResponseEntity<ClientPatchDto> updateStatus(@PathVariable boolean status,
                                                       @PathVariable String cpf){
        return ResponseEntity.ok(clientService.clientUpdateStatus(status, cpf));
    }

    @DeleteMapping("/{cpf}")
    public void delete(@PathVariable String cpf){
        clientService.clientDelete(cpf);
    }


}
