package com.victorlucas.cursomc.controllers;

import com.victorlucas.cursomc.domain.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @GetMapping
    public List<Categoria> listar(){
        Categoria categoria = new Categoria(1L,"Informática");
        Categoria categoria2 = new Categoria(2L,"Escritório");

        List<Categoria> lista = new ArrayList<>();
        
        lista.add(categoria);
        lista.add(categoria2);

        return lista;
    }

}
