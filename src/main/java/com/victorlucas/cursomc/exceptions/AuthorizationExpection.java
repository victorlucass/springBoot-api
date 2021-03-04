package com.victorlucas.cursomc.exceptions;

public class AuthorizationExpection extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public AuthorizationExpection(String mensagem){
        super(mensagem);
    }

    public AuthorizationExpection(String mensagem, Throwable causa){
        super(mensagem,causa);
    }
}
