package com.gusscarros.core.cliente.controller;

import com.gusscarros.core.cliente.model.User;
import com.gusscarros.core.cliente.service.crud.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

     SaveUser save;
     FindAllUser findAllUser;
     FindName findName;
     FindCpf findCpf;
     UpdateUser updateUser;
     DeleteUser deleteUser;

    public UserController(SaveUser save, FindAllUser findAllUser, FindName findName,
                          FindCpf findCpf, UpdateUser updateUser, DeleteUser deleteUser) {
        this.save = save;
        this.findAllUser = findAllUser;
        this.findName = findName;
        this.findCpf = findCpf;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
    }



    @PostMapping("/save")
    public ResponseEntity<User> save(@Valid @RequestBody User user){
         return ResponseEntity.ok(save.saveCliente(user));
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return findAllUser.allCliente();
    }

    @GetMapping("/searchname")
    public List<User> findByName(@RequestParam("name") String name){

        return findName.searchName(name);

    }

    @GetMapping("/searchcpf")
    public User findByCpf(@RequestParam("cpf") String cpf){

        return findCpf.searchCpf(cpf);

    }

    @PutMapping("/update/{id}")
    public User update(@RequestBody User newUser, @PathVariable String id){

        return updateUser.clienteUpdate(newUser, id);
    }

    @PutMapping("/delete/{id}")
    public User delete(@RequestBody User newUser, @PathVariable String id){

        return deleteUser.clienteDelete(newUser, id);

    }


}
