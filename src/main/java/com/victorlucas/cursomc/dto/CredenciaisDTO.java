package com.victorlucas.cursomc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredenciaisDTO implements Serializable {
    public static final long serialVersionUID = 1L;
    private String email;
    private String senha;
}
