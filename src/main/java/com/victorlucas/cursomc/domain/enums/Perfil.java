package com.victorlucas.cursomc.domain.enums;

import lombok.Getter;

@Getter
public enum Perfil {
    ADMIN (1, "ROLE_ADMIN"), //"ROLE_" é um prefixo obrigatório do Spring.
    CLIENTE (2, "ROLE_CLIENTE");

    private Integer number;
    private String description;

    private Perfil(Integer number, String description){
        this.number = number;
        this.description = description;
    }

    public static Perfil toEnum(Integer cod){
        if (cod == null){
            return null;
        }

        for (Perfil x : Perfil.values()){
            if (cod.equals(x.getNumber())){
                return x;
            }
        }

        throw new IllegalArgumentException("O id" + cod + " é inválido.");
    }
}
