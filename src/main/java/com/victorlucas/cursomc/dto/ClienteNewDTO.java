package com.victorlucas.cursomc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ClienteNewDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String cpfOuCnpj;
    private Integer tipoCliente;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    private String telefone1;
    private String telefone2;
    private String telefone3;

    private Integer cidadeId;

}
