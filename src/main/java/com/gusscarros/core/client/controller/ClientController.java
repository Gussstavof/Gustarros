package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.dto.ClientGetDto;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.dto.ClientPutDto;
import com.gusscarros.core.client.model.Client;
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
    public ResponseEntity<ClientPostDto> save(@Valid @RequestBody ClientPostDto clientPostDto, URI location){
        clientService.saveClient(clientPostDto.convert());
        return ResponseEntity.created(location).body(clientPostDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientGetDto>> getAll(){
        List<Client> client = clientService.allClient();
        return ResponseEntity.ok(ClientGetDto.convertListDto(client));
    }

    @GetMapping("/searchname")
    public ResponseEntity<List<ClientGetDto> >findByName(@RequestParam("name") String name){
        List<Client> client = clientService.searchName(name);
        return ResponseEntity.ok(ClientGetDto.convertListDto(client));
    }

    @GetMapping("/searchcpf")
    public ResponseEntity<ClientGetDto> findByCpf(@RequestParam("cpf") String cpf){
        Client client = clientService.searchCpf(cpf);
        return ResponseEntity.ok(ClientGetDto.convertClientDto(client));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClientPutDto> update(@Valid @RequestBody ClientPutDto newClient, @PathVariable String cpf
                                               ,URI uri){
        Client client = clientService.clientUpdate(newClient.convert(),cpf);
        return ResponseEntity.created(uri).body(new ClientPutDto(client));
    }

    @PatchMapping("/{cpf}/{status}")
    public ResponseEntity<ClientPatchDto> updateStatus(@PathVariable boolean status, @PathVariable String cpf
                                                       ,URI uri){
        Client client = clientService.clientUpdateStatus(status, cpf);
        return ResponseEntity.created(uri).body(ClientPatchDto.convert(client));
    }

    @DeleteMapping("/{cpf}")
    public void delete(@PathVariable String cpf){
        clientService.clientDelete(cpf);
    }


}
