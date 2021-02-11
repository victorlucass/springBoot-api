package com.victorlucas.cursomc.config;

import com.victorlucas.cursomc.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategyDB; //Armazena o valor da chave nessa variável.


    @Bean
    public boolean instantiateDatabase() throws ParseException {
        if ("create".equals(strategyDB)){
            dbService.instantiateTestDatabase();
            return true;
        }
        return false;
    }
}
