package com.victorlucas.cursomc.services.security;

import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.repositories.ClienteRepository;
import com.victorlucas.cursomc.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = repo.findByEmail(email);
        if (cliente == null){
            throw new UsernameNotFoundException(email);//Exception do UserDetails
        }
        return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
    }
}
