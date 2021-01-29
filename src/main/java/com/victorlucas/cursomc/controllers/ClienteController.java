package com.victorlucas.cursomc.controllers;

import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> findAll(){
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id){
        Cliente clienteSelect =  clienteService.findById(id);
        return ResponseEntity.ok().body(clienteSelect);
    }
}
