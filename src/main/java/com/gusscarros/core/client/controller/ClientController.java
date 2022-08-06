package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientDto;
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
    public ResponseEntity<ClientDto> save(@Valid @RequestBody ClientDto clientDto,
                                          URI location){
        return ResponseEntity.created(location).body(clientService.saveClient(clientDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientDto>> getAll(){
        return ResponseEntity.ok(clientService.allClient());
    }

    @GetMapping("/searchname")
    public ResponseEntity<List<ClientDto> >findByName(@RequestParam("name") String name){
        return ResponseEntity.ok(clientService.searchName(name));
    }

    @GetMapping("/searchcpf")
    public ResponseEntity<ClientDto> findByCpf(@RequestParam("cpf") String cpf){
        return ResponseEntity.ok(clientService.searchCpf(cpf));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClientDto> update(@Valid @RequestBody ClientDto clientDto,
                                               @PathVariable String cpf
                                               ,URI uri){
        return ResponseEntity.created(uri).body(clientService.clientUpdate(clientDto ,cpf));
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
