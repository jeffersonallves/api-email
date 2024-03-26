package dev.jefferson.email.controllers;

import dev.jefferson.email.entities.Email;
import dev.jefferson.email.entities.EmailLog;
import dev.jefferson.email.enuns.StatusEmail;
import dev.jefferson.email.repositories.EmailRepositoryCustom;
import dev.jefferson.email.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {


    @InjectMocks
    EmailController controller;

    @Mock
    private EmailService service;

    @Mock
    private EmailRepositoryCustom emailRepositoryCustom;

    MockMvc mockMvc;
    private Email email;
    @Mock
    private Page<EmailLog> emailLogPage;
    private UUID emailId;
    private String emailLogDate;
    private MockMultipartFile file;
    private Pageable pageable;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .alwaysDo(print())
                .build();
        file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        email = new Email(
                UUID.randomUUID(),
                "Jefferson",
                "jefferson.alves.rodrigues@gmail.com",
                "userdevelopmenttests@gmail.com",
                null,
                null,
                "Envio via mensageria",
                "Olá! Tudo bem? Estou fazendo testes de envio de e-mail",
                LocalDateTime.now(),
                StatusEmail.SENT,
                null,
                null,
                "email-template");

        String uuid = "5cf88a41-4b4a-4a4c-979f-5acd3f4649b4";
        emailId = UUID.fromString(uuid);
        emailLogDate = "28/11/2023 17:09:04";
        pageable = PageRequest.of(0, 5);

    }

    @Test
    @DisplayName("Get the request and send the simple email successfully")
    public void getTheRequestAndSendTheSimpleEmailSuccessfully() throws Exception {
        when(service.send(email)).thenReturn(email);

        mockMvc.perform(post("/v1/emails/sending")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("emailDto", email.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        verify(service).send(email);
        verifyNoMoreInteractions(service);

    }

    @Test
    @DisplayName("Get the request and send the complete email successfully")
    public void getTheRequestAndSendTheCompleteEmailSuccessfully() throws Exception {
        when(service.sendEmailComplete(email, Collections.singletonList(file), null)).thenReturn(email);

        mockMvc.perform(multipart("/v1/emails/send-email-general")
                        .file(file)
                        .param("emailTo", email.getEmailTo())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        verify(service).sendEmailComplete(email, Collections.singletonList(file), null);
        verifyNoMoreInteractions(service);

    }


    @Test
    @DisplayName("Receive the request and return the logs to the database successfully")
    public void getTheRequestAndReturnTheLogsToTheDatabaseSuccessfully() throws Exception {
        when(emailRepositoryCustom.findLogEmailByIdAndDate(emailLogDate, emailId, null)).thenReturn(emailLogPage);

        mockMvc.perform(get("/v1/emails/logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("emailLogDate", emailLogDate.toString())
                        .param("emailId", emailId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(emailRepositoryCustom).findLogEmailByIdAndDate(emailLogDate, emailId, null);
        verifyNoMoreInteractions(service);

    }
}