package com.victorlucas.cursomc.domain;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Categoria implements Serializable {
    public static final long serialVersionUID = 1L;
    private Long id;
    private String name;
}
