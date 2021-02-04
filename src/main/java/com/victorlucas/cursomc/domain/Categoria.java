package com.victorlucas.cursomc.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Categoria implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos = new ArrayList<>();
    /* mappedBy é usado para definir que a entidade Categoria não é dominante na relação com Produto,
         no nome que está inserido deve ser o mesmo na variável lá no Produtos */

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria(Integer id, String nome) {
    }
}
