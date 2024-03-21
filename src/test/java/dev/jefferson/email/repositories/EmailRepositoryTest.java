package dev.jefferson.email.repositories;

import dev.jefferson.email.dtos.EmailDTO;
import dev.jefferson.email.entities.Email;
import dev.jefferson.email.enuns.StatusEmail;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class EmailRepositoryTest {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return the searched email")
    void findByStatusEmailAndDateSuccess() {
        EmailDTO emailDTO = new EmailDTO("cadastroconta2022@gmail.com", null, null, "subject", "text", null, null);
        this.createEmail(emailDTO);

        List<Email> foundEmail = this.emailRepository.findByStatusEmailAndDate(StatusEmail.SENT, "18/11/2023 11:00:00");

        assertThat(foundEmail).isNotEmpty();
    }

    @Test
    @DisplayName("Should not return the searched email when it does not exist")
    void findByStatusEmailAndDateFailed() {

        List<Email> foundEmail = this.emailRepository.findByStatusEmailAndDate(StatusEmail.SENT, "01/01/2022 00:00:00");

        assertThat(foundEmail).isEmpty();
    }

    private Email createEmail(EmailDTO emailDTO) {
        Email email = new Email(emailDTO);
        email.setSendDateEmail(LocalDateTime.now());
        email.setStatusEmail(StatusEmail.SENT);
        this.entityManager.persist(email);
        return email;
    }
}