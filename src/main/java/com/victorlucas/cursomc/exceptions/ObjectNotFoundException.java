package com.victorlucas.cursomc.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    public static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String mensagem){
        super(mensagem);
        /* Ele adiciona a mensagem no RunTimeException */
    }

    public ObjectNotFoundException(String mensagem, Throwable cause){
        super(mensagem,cause);
    }
}
