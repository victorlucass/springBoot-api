package com.victorlucas.cursomc.services.email;

import com.victorlucas.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")//Pegando o valor lá do application.properties.
    private String send;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage mailMessage = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(mailMessage);
    }


    private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(pedido.getCliente().getEmail());//Pra quem vai enviar?
        message.setFrom(send);//Quem vai enviar?
        message.setSubject("Pedido confirmado! Código: " + pedido.getId());//Qual o assunto?
        message.setSentDate(new Date(System.currentTimeMillis()));//Qual o momento do envio?
        message.setText(pedido.toString());//O corpo do pedido.
        return message;
    }

}
