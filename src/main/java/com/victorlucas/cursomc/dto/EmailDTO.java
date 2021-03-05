package com.victorlucas.cursomc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO implements Serializable {
    public static final long serialVersionUID = 1L;


    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido.")
    private String email;
}
