package com.victorlucas.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
    };

    //Essa lista acima é as rotas liberadas, ou seja, sem autorização.

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/produtos/**",
            "/categorias/**"
    };
    //Essa lista acima é as rotas liberadas apenas para leitura, ou seja, sem autorização.

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (Arrays.asList(environment.getActiveProfiles()).contains("test")){
            http.headers().frameOptions().disable();
        }

        //Esse cara é para liberar acesso ao h2

        http.cors().and().csrf().disable();
        //cors(), o spring vai pegar como base o métodos corsConfigurationSource feito lá em baixo.
        //.and().csrf().disable(); ele desativa proteção contra csrf.

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //apenas o método GET
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest()
                .authenticated();
        // Chama o authorizeRequests, depois chama antMatchers para pegar a lista de vetor,
        // e chama permitAll para todos os caminhos que tiverem na lista de rotas serem liberadas.
        // .anyRequest().authenticated(), ou seja, "para todo o resto, exigir"

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //Para segurar que nosso backend não vai criar sessão de usuário, usando o STATELESS.

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    //Esse método é um "padrão", ele dá o acesso básico em múltiplas fontes para todos os caminhos,
}
