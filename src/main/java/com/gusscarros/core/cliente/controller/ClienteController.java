package com.gusscarros.core.cliente.controller;

import com.gusscarros.core.cliente.model.Cliente;
import com.gusscarros.core.cliente.repository.ClienteRepository;
import com.gusscarros.core.cliente.service.crud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class ClienteController {

     SaveCliente save;
     FindAllCliente findAllCliente;
     FindName findName;
     FindCpf findCpf;
     UpdateCliente updateCliente;
     DeleteCliente deleteCliente;

    public ClienteController(SaveCliente save, FindAllCliente findAllCliente, FindName findName,
                             FindCpf findCpf, UpdateCliente updateCliente, DeleteCliente deleteCliente) {
        this.save = save;
        this.findAllCliente = findAllCliente;
        this.findName = findName;
        this.findCpf = findCpf;
        this.updateCliente = updateCliente;
        this.deleteCliente = deleteCliente;
    }

    @Autowired
    ClienteRepository repository;

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
