package com.victorlucas.cursomc.config;

import com.victorlucas.cursomc.services.DBService;
import com.victorlucas.cursomc.services.email.EmailService;
import com.victorlucas.cursomc.services.email.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService (){
        return new MockEmailService();
    }
}
