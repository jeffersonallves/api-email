package dev.jefferson.email.controllers;

import dev.jefferson.email.dtos.ContextVariableDTO;
import dev.jefferson.email.dtos.EmailDTO;
import dev.jefferson.email.entities.Email;
import dev.jefferson.email.entities.EmailLog;
import dev.jefferson.email.enuns.StatusEmail;
import dev.jefferson.email.repositories.EmailRepositoryCustom;
import dev.jefferson.email.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(value = "v1/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRepositoryCustom emailRepositoryCustom;

    @PostMapping("/send")
    public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDTO emailDTO) {

        Email email = new Email(emailDTO);
        emailService.send(email);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @PostMapping(value = "/send-with-attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Email> sendWithAttachment(@RequestPart(name = "email") @Valid EmailDTO emailDTO,
                                                    @RequestPart(name = "files") List<MultipartFile> files) throws MessagingException  {
        Email email = new Email(emailDTO);
        emailService.sendWithAttachment(email, files);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @PostMapping("/send-email-template")
    public ResponseEntity<Email> sendEmailTemplate(@RequestBody @Valid EmailDTO emailDTO) throws MessagingException {
        Email email = new Email(emailDTO);
        Map<String, String> map = new HashMap<String, String>();
        for(ContextVariableDTO variable : emailDTO.getVariables()) {
            map.put(variable.getName(), variable.getValue());
        }
        emailService.sendEmailTemplate(email, map);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Email>> getAllEmails(@PageableDefault(size = 5, sort = "sendDateEmail", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id/{idEmail}")
    public ResponseEntity<Object> findById(@PathVariable @NotNull(message = "The id cannot be null") UUID idEmail) {
        Optional<Email> email = emailService.findById(idEmail);
        if(email.isPresent()) {
            return new ResponseEntity<>(email.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found: " + idEmail);
        }
    }

    @RequestMapping(value = "/status-and-date", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Email>> findByStatusEmailAndDate(@RequestParam String statusEmail,
                                                                              @RequestParam String sendDateEmail) {
        Email email = new Email();
        email.setStatusEmail(StatusEmail.valueOf(statusEmail));
        return new ResponseEntity<>(emailService.findByStatusEmailAndDate(email.getStatusEmail(), sendDateEmail), HttpStatus.OK);
    }

    @GetMapping("/status/{statusEmail}")
    public ResponseEntity<Page<Email>> findByStatusEmail(@PageableDefault(size = 5, sort = "sendDateEmail", direction = Sort.Direction.DESC) Pageable pageable,
                                                         @PathVariable @NotBlank(message = "The status cannot be empty") String statusEmail) {
        Email email = new Email();
        email.setStatusEmail(StatusEmail.valueOf(statusEmail));
        return new ResponseEntity<>(emailService.findByStatusEmail(email.getStatusEmail(), pageable), HttpStatus.OK);
    }

    @GetMapping("/send-date/{sendDateEmail}")
    public ResponseEntity<Page<Email>> findBySendDateEmailAfter(@PageableDefault(size = 5, sort = "sendDateEmail", direction = Sort.Direction.DESC) Pageable pageable,
                                                                @PathVariable @NotNull(message = "The send date cannot be null") LocalDateTime sendDateEmail) {
        return new ResponseEntity<>(emailService.findBySendDateEmailAfter(sendDateEmail, pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/date-between", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Page<Email>> findBySendDateEmailBetween(@PageableDefault(size = 5, sort = "sendDateEmail", direction = Sort.Direction.DESC) Pageable pageable,
                                                                                @RequestParam LocalDateTime startDateEmail,
                                                                                @RequestParam LocalDateTime endDateEmail) {
        return new ResponseEntity<>(emailService.findBySendDateEmailBetween(startDateEmail, endDateEmail, pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Page<EmailLog>> findLogEmailByIdAndDate(@PageableDefault(size = 5, sort = "sendDateEmail", direction = Sort.Direction.DESC) Pageable pageable,
                                                                                @RequestParam(value = "emailLogDate", required = false) String emailLogDate,
                                                                                @RequestParam(value = "idEmail", required = false) UUID idEmail) throws Exception {
        return new ResponseEntity<>(emailRepositoryCustom.findLogEmailByIdAndDate(emailLogDate, idEmail, pageable), HttpStatus.OK);
    }
}
