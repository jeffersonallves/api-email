package dev.jefferson.email.consumers;

import dev.jefferson.email.dtos.EmailDTO;
import dev.jefferson.email.entities.Email;
import dev.jefferson.email.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    @Autowired
    EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDTO emailDto) throws MessagingException {
        Email email = new Email();
        BeanUtils.copyProperties(emailDto, email);
        emailService.sendEmailComplete(email, null, null);
        System.out.println("Email Status: " + email.getStatusEmail().toString());
    }
}
