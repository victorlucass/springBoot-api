package com.victorlucas.cursomc.services.email;

import com.mysql.cj.xdevapi.Client;
import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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

    private String htmlFromTemplatePedido(Pedido pedido) {
        Context context = new Context(); //Esse cara que vai mandar as informações para o HTML.
        context.setVariable("pedido",pedido);//Apelido do obj e o próprio obj.
        return templateEngine.process("email/confirmacaoPedido", context);//Vai retorna o html em string.
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(pedido);
            sendHtmlEmail(mm);
        }catch (MessagingException ex){
            sendOrderConfirmationEmail(pedido);
        }
    }

    private MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(pedido.getCliente().getEmail());
        mmh.setFrom(send);
        mmh.setSubject("Pedido confirmado! Código: " + pedido.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(pedido), true);
        return mimeMessage;
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPassword) {
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPassword);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPassword){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cliente.getEmail());//Pra quem vai enviar?
        message.setFrom(send);//Quem vai enviar?
        message.setSubject("Solicitação de nova senha");//Qual o assunto?
        message.setSentDate(new Date(System.currentTimeMillis()));//Qual o momento do envio?
        message.setText("Nova senha: " + newPassword);//O corpo do pedido.
        return message;

    }
}
