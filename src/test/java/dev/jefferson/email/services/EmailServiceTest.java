package dev.jefferson.email.services;

import dev.jefferson.email.dtos.EmailDTO;
import dev.jefferson.email.entities.Email;
import dev.jefferson.email.enuns.StatusEmail;
import dev.jefferson.email.repositories.EmailAttachRepository;
import dev.jefferson.email.repositories.EmailLogRepository;
import dev.jefferson.email.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@TestPropertySource(properties = { "spring.mail.username=userdevelopmenttests@gmail.com" })
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailAttachRepository emailAttachRepository;

    @Mock
    private EmailLogRepository emailLogRepository;

    @Autowired
    @InjectMocks
    private EmailService emailService;

    Email newEmail;

    @Mock
    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        newEmail = new Email(null, null, "cadastroconta2022@gmail.com", "userdevelopmenttests@gmail.com", null, null, "subject", "text test", LocalDateTime.now(), StatusEmail.SENT, null, null, null);
    }

    @Test
    @DisplayName("Should send an email when everything is OK")
    void sendSuccess() {

        ArgumentCaptor<MimeMessage> mimeMessageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        doNothing().when(mailSender).send(mimeMessage);

        emailService.send(newEmail);

        verify(mailSender, times(1)).send((mimeMessageArgumentCaptor.capture()));
        verifyNoMoreInteractions(mailSender);
        verify(emailRepository, times(1)).save(newEmail);
        verifyNoMoreInteractions(emailRepository);

    }

    @Test
    @DisplayName("Should throw Exception when failed to send an email")
    void sendFailed() {
        Email email = new Email();

        email.setEmailTo("invalid@mail");
        email.setEmailFrom("invalid@mail");
        email.setSubject("subject");

        ArgumentCaptor<MimeMessage> mimeMessageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        doNothing().when(mailSender).send(mimeMessage);
        emailService.send(email);

        verify(mailSender, times(1)).send((mimeMessageArgumentCaptor.capture()));
        verifyNoMoreInteractions(mailSender);
        verify(emailRepository, times(1)).save(email);
        verifyNoMoreInteractions(emailRepository);
        verify(emailLogRepository, times(1)).save(any());
        verifyNoMoreInteractions(emailLogRepository);
    }

    @Test
    @DisplayName("Should send an email with attachment when everything is OK")
    void sendWithAttachmentSuccess() {
    }

    @Test
    @DisplayName("Should throw Exception when failed to send an email with attachment")
    void sendWithAttachmentFailed() {

    }

    @Test
    @DisplayName("Should send an email template when everything is OK")
    void sendEmailTemplate() {
    }

    @Test
    @DisplayName("Should throw Exception when failed to send an email template")
    void sendEmailTemplateFailed() {

    }
}