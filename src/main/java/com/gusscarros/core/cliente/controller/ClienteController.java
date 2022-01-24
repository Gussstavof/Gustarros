package com.gusscarros.core.cliente.controller;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class ClienteController {

    @Autowired SaveCliente save;
    @Autowired FindAllCliente findAllCliente;
    @Autowired FindName findName;
    @Autowired FindCpf findCpf;
    @Autowired UpdateCliente updateCliente;
    @Autowired DeleteCliente deleteCliente;



    @PostMapping("/save")
    public Cliente save(@Valid @RequestBody Cliente cliente){

        return save.saveCliente(cliente);
    }

    @GetMapping("/all")
    public List<Cliente> getAll(){
        return findAllCliente.allCliente();
    }

    @GetMapping("/searchname")
    public List<Cliente> findByNome(@RequestParam("nome") String nome){

        return findName.searchName(nome);
    }

    @GetMapping("/searchcpf")
    public Cliente findByCpf(@RequestParam("cpf") String cpf){

        return findCpf.searchCpf(cpf);

    }

    @PutMapping("/update/{id}")
    public Cliente update(@RequestBody Cliente newCliente, @PathVariable String id){

        return updateCliente.clienteUpdate(newCliente, id);
    }

    @PutMapping("/delete/{id}")
    public Cliente delete(@RequestBody Cliente newCliente, @PathVariable String id){

        return deleteCliente.clienteDelete(newCliente, id);

    }


}
