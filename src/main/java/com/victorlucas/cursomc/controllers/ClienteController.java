package com.victorlucas.cursomc.controllers;

import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.dto.ClienteDTO;
import com.victorlucas.cursomc.dto.ClienteNewDTO;
import com.victorlucas.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /*GET*/

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDTO> dtos = clientes.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id){
        Cliente clienteSelect =  clienteService.findById(id);
        return ResponseEntity.ok().body(clienteSelect);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Cliente>> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linePerPage", defaultValue = "24") Integer linePerPage,
            @RequestParam(value = "direction", defaultValue = "nome") String direction,
            @RequestParam(value = "orderBy", defaultValue = "ASC") String orderBy){

        Page<Cliente> list = clienteService.findPage(page,linePerPage,direction,orderBy);
        return ResponseEntity.ok().body(list);
    }

    /*POST*/
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteNewDTO dto){
        Cliente body = clienteService.save(dto);
        return ResponseEntity.ok().body(body);
    }

    /*PUT*/
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Integer id, @RequestBody @Valid ClienteDTO dto){
        Cliente body = clienteService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    /*DELETE*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

