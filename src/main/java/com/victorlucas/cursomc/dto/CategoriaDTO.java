package com.victorlucas.cursomc.dto;

import com.victorlucas.cursomc.domain.Categoria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public CategoriaDTO(Categoria categoria){
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
}
