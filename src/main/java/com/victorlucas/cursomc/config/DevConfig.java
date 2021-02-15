package com.victorlucas.cursomc.config;

import com.victorlucas.cursomc.services.DBService;
import com.victorlucas.cursomc.services.email.EmailService;
import com.victorlucas.cursomc.services.email.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;

import javax.validation.constraints.Email;
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

    @Bean//Só vai executar esse cara quando rodar com dev
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}
