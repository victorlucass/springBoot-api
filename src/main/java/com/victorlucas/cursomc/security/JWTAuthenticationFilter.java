package com.victorlucas.cursomc.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorlucas.cursomc.dto.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //attemptAuthentication -> tenta autenticar.
        try{
            CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
            //Para pegar o login(email/senha) no contexto do método, ele chama o DTO.
            //new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class) -> vai pegar oq tá vindo da requisição e transformar em DTO. E injeta no DTO.

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(),
                    new ArrayList<>()/*Precisa passar uma lista vazia*/);
            //Após pegar oq tá vindo da requisição e converter pra um objeto, injeta o objeto na instância de UsernamePasswordAuthenticationToken
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth; //Esse é o cara que vai validar se o login e senha são válidos com base nos UserDatails/Service

        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    //Ok a requisição deu certo! Oq é pra fazer? Aí q entra o successfulAuthentication.

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String username = ((UserSpringSecurity) authResult.getPrincipal()/*Vai retornar o user do spring security*/).getUsername();
        String token = jwtUtil.generateToken(username);//Chama o método lá o JWTUtil e gera o token para o usuário...
        response.addHeader("Authorization", "Bearer " + token);//Adiciona no header na resposta da requisição...
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
