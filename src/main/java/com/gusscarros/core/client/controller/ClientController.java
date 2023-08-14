package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientDto;
import com.gusscarros.core.client.dto.request.ClientRequest;
import com.gusscarros.core.client.dto.response.ClientResponse;
import com.gusscarros.core.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@Valid @RequestBody ClientRequest clientRequest, URI location) {
        String response = clientService.saveClient(clientRequest);

        return ResponseEntity
                .created(location)
                .body(String.format(
                        """
                                 {
                                      "message": "CREATED",
                                      "cpf": "%s"
                                 }
                                """
                        , response)
                );
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ClientDto>> getAll(@PageableDefault(sort = "{name}") Pageable pageable){
        return ResponseEntity.ok(clientService.allClient(pageable));
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
