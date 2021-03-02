package com.victorlucas.cursomc.security;

import com.victorlucas.cursomc.domain.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserSpringSecurity implements UserDetails {
    public static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public Integer getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //A conta não está expirada? Caso houve alguma necessidade de expirar a conta do usuário.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //A conta não está bloqueada?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //As credenciais não estão expiradas?
        return true;
    }

    @Override
    public boolean isEnabled() {
        //O usuário está ativo?
        return true;
    }

    public UserSpringSecurity(Integer id, String email, String senha, Set<Perfil> perfils) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = perfils.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toSet()); //Transforma o perfil em Collection<? extends GrantedAuthority>
    }
}
