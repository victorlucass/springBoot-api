package com.victorlucas.cursomc.security;

import io.jsonwebtoken.Claims;
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

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (username != null && expirationDate != null && now.before(expirationDate)){
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null){
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        }catch (Exception ex){
            return null;
        }
    }

}
