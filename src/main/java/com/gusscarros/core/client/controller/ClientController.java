package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.models.request.ClientRequest;
import com.gusscarros.core.client.models.response.ClientResponse;
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


@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@Valid @RequestBody ClientRequest clientRequest, URI location) {
        String response = clientService.save(clientRequest);

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
    public ResponseEntity<Page<ClientResponse>> getAll(@PageableDefault(sort = "{name}") Pageable pageable){
        return ResponseEntity.ok(clientService.getAll(pageable));
    }

    @GetMapping("/searchname")
    public ResponseEntity<List<ClientResponse> >findByName(@RequestParam("name") String name){
        return ResponseEntity.ok(clientService.searchByName(name));
    }

    @GetMapping("/searchcpf")
    public ResponseEntity<ClientResponse> findByCpf(@RequestParam("cpf") String cpf){
        return ResponseEntity.ok(clientService.searchByCpf(cpf));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClientResponse> update(@Valid @RequestBody ClientRequest clientRequest,
                                               @PathVariable String cpf
                                               ,URI uri){
        return ResponseEntity.created(uri)
                .body(clientService.update(clientRequest, cpf));
    }

    @PatchMapping(path = "/{cpf}/{status}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> updateStatus(@PathVariable boolean status,
                                                       @PathVariable String cpf){
        ClientResponse response = clientService.updateStatus(status, cpf);

        return ResponseEntity
                .ok(String.format(
                        """
                                 {
                                      "cpf": "%s",
                                      "status": "%s"
                                 }
                                """
                        , response.getCpf()
                        , response.isStatus()
                        )
                );
    }

    @DeleteMapping("/{cpf}")
    public void delete(@PathVariable String cpf){
        clientService.deleteByCpf(cpf);
    }


}
