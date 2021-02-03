package com.victorlucas.cursomc.controllers;

import com.victorlucas.cursomc.domain.Categoria;
import com.victorlucas.cursomc.dto.CategoriaDTO;
import com.victorlucas.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    /*GET*/
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<CategoriaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Categoria> list = service.findPage(page,linesPerPage,direction,orderBy);
        Page<CategoriaDTO>listDTO = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria>findById(@PathVariable Integer id){
        Categoria categoria = service.findById(id);
        return ResponseEntity.ok().body(categoria);
    }

    /*POST*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria){
        Categoria obj = service.save(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    /*PUT*/

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@RequestBody Categoria categoria ,@PathVariable Integer id){
        categoria.setId(id);
        Categoria body = service.update(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    /*DELETE*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
