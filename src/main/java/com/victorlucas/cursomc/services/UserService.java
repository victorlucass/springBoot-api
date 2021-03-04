package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.security.UserSpringSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {
    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //Esse cara retorna o usuário logado.
        } catch (Exception e){
            return null;
        }
    }
}
