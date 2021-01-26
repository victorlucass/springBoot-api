package com.victorlucas.cursomc.domain.enums;

import lombok.Getter;

@Getter
public enum TipoCliente {
    PESSOAFISICA (1, "Pessoa Física"),
    PESSOAJURIDICA (2, "Pessoa Jurídica");

    private int number;
    private String description;

    private TipoCliente(int number, String description){
        this.number = number;
        this.description = description;
    }

    public static TipoCliente toEnum(Integer cod){
        if (cod == null){
            return null;
        }

        for (TipoCliente x : TipoCliente.values()){
            if (cod.equals(x.getNumber())){
                return x;
            }
        }

        throw new IllegalArgumentException("O id" + cod + " é inválido.");
    }
}
