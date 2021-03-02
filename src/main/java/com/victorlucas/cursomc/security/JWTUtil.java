package com.victorlucas.cursomc.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username){
        return Jwts.builder() //É o cara que vai gerar o token
                .setSubject(username) //É o usuário
                .setExpiration(new Date(System.currentTimeMillis() + expiration))//Tempo atual do sistema mais a expiração.
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8)) // Isso é necessário para dizer como vai ser assinado o token. (O Algoritmo + segredo)
                .compact(); //Para finalizar
    }
}
