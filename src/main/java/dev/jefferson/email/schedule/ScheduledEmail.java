package dev.jefferson.email.schedule;

import dev.jefferson.email.entities.Email;
import dev.jefferson.email.enuns.StatusEmail;
import dev.jefferson.email.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="spring.task.scheduling.enabled", matchIfMissing = true)
@PropertySource("classpath:application.properties")
public class ScheduledEmail {

    private static final String cronExpression = "*/30 * * ? * *";
    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "${spring.task.scheduling.cron}", zone = "${spring.task.scheduling.zone}")
    public void execute() throws InterruptedException, MessagingException {
        sendEmail(generateEmail());
    }

    private void sendEmail(List<Email> listEmail) throws MessagingException{
        if(!listEmail.isEmpty()) {
            for(Email email : listEmail) {
                emailService.sendEmailComplete(email, null, null);
            }
        }
    }

    private List<Email> generateEmail() {

        List<Email> listEmail = new ArrayList<Email>();
        Page<Email> pageEmail = emailService.findByStatusEmail(StatusEmail.SCHEDULED, Pageable.unpaged());
        for(Email email : pageEmail) {
            listEmail.add(email);
        }

        return listEmail;
    }
}
