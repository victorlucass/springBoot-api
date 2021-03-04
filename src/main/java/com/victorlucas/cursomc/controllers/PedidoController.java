package com.victorlucas.cursomc.controllers;

import com.victorlucas.cursomc.domain.Categoria;
import com.victorlucas.cursomc.domain.Pedido;
import com.victorlucas.cursomc.dto.CategoriaDTO;
import com.victorlucas.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Integer id){
        Pedido pedido =  pedidoService.findById(id);
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> create(@RequestBody Pedido pedido){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
        Pedido body = pedidoService.save(pedido);
        return ResponseEntity.created(uri).body(body);
    }

    @GetMapping()
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction){
        Page<Pedido> list = pedidoService.findPage(page,linesPerPage,direction,orderBy);
        return ResponseEntity.ok().body(list);

    }

}
