package dev.jefferson.email.services;

import dev.jefferson.email.entities.Email;
import dev.jefferson.email.entities.EmailAttach;
import dev.jefferson.email.entities.EmailLog;
import dev.jefferson.email.enuns.StatusEmail;
import dev.jefferson.email.implementations.EmailRepositoryImpl;
import dev.jefferson.email.repositories.EmailAttachRepository;
import dev.jefferson.email.repositories.EmailLogRepository;
import dev.jefferson.email.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailAttachRepository emailAttachRepository;

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Autowired
    private EmailRepositoryImpl emailRepositoryImpl;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${spring.mail.default-encoding}")
    private String ENCODING;

    @Transactional
    public Email send(Email email) {
        EmailLog log = null;

        if(!StringUtils.isEmpty(email.getOwnerRef()))
            email.setOwnerRef(getLoggedInUser());

        email.setSendDateEmail(LocalDateTime.now());
        email.setEmailFrom(emailFrom);

        try {

            mailSender.send(assignedEmail(email));

            email.setStatusEmail(StatusEmail.SENT);

        } catch(MailException | MessagingException e) {
            e.printStackTrace();
            email.setStatusEmail(StatusEmail.ERROR);
            log = assignedEmailLogError(email, e.getMessage());
        } finally {
            emailRepository.save(email);
            if(log != null)
                emailLogRepository.save(log);
        }

        return email;
    }

    @Transactional
    public Email sendWithAttachment(Email email, List<MultipartFile> files) throws MessagingException {
        EmailLog log = null;

        if(!StringUtils.isEmpty(email.getOwnerRef()))
            email.setOwnerRef(getLoggedInUser());

        email.setSendDateEmail(LocalDateTime.now());
        email.setEmailFrom(emailFrom);

        try {

            MimeMessage message = assignedEmail(email);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, ENCODING);
            helper.setPriority(1);

            for(MultipartFile file : files) {
                EmailAttach emailAttach = new EmailAttach();

                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                helper.addAttachment(fileName, file);
                emailAttach.setAttach(file.getBytes());
                emailAttach.setEmail(email);
                email.getEmailAttach().add(emailAttach);
            }

            helper.setText(email.getText(), true);

            mailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);

        } catch(MailException | IOException e) {
            e.printStackTrace();
            email.setStatusEmail(StatusEmail.ERROR);
            log = assignedEmailLogError(email, e.getMessage());
        } finally {
            emailRepository.save(email);
            if(email.getEmailAttach().size() > 0)
                emailAttachRepository.saveAll(email.getEmailAttach());
            if(log != null)
                emailLogRepository.save(log);
        }

        return email;
    }

    @Transactional
    public Email sendEmailTemplate(Email email, Map<String, String> params) throws MessagingException {
        EmailLog log = null;

        if(!StringUtils.isEmpty(email.getOwnerRef()))
            email.setOwnerRef(getLoggedInUser());

        TemplateEngine templateEngine = new TemplateEngine();
        email.setSendDateEmail(LocalDateTime.now());
        email.setEmailFrom(emailFrom);

        try {

            MimeMessage mimeMessage = assignedEmail(email);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            helper.setPriority(1);

            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix("templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode(TemplateMode.HTML);
            templateResolver.setCacheable(false);
            templateResolver.setOrder(1);
            templateResolver.setCharacterEncoding(ENCODING);

            templateEngine.setTemplateResolver(templateResolver);
            String htmlContent = templateEngine.process(email.getTemplateName(), buildContext(params));

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

            email.setStatusEmail(StatusEmail.SENT);

        } catch(MailException e) {
            e.printStackTrace();
            email.setStatusEmail(StatusEmail.ERROR);
            log = assignedEmailLogError(email, e.getMessage());
        } finally {
            emailRepository.save(email);
            if(log != null)
                emailLogRepository.save(log);
        }

        return email;
    }

    public Page<Email> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

    public Optional<Email> findById(UUID idEmail) {
        return emailRepository.findById(idEmail);
    }

    public List<Email> findByStatusEmailAndDate(StatusEmail statusEmail, String sendDateEmail) {
        System.out.println(statusEmail);
        return emailRepository.findByStatusEmailAndDate(statusEmail, sendDateEmail);
    }

    public Page<Email> findByStatusEmail(StatusEmail statusEmail, Pageable pageable) {
        return emailRepository.findByStatusEmail(statusEmail, pageable);
    }

    public Page<Email> findBySendDateEmailBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return emailRepository.findBySendDateEmailBetween(start, end, pageable);
    }

    public Page<Email> findBySendDateEmailAfter(LocalDateTime start, Pageable pageable) {
        return emailRepository.findBySendDateEmailAfter(start, pageable);
    }

    private Context buildContext(Map<String, String> params) {
        Context context = new Context();
        if(params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
        }
        return context;
    }

    private EmailLog assignedEmailLogError(Email email, String message) {

        EmailLog log = new EmailLog();

        log.setEmailLogDate(email.getSendDateEmail());
        log.setEmailLogDesc(message);
        log.setEmail(email);

        return log;
    }

    private void saveEmailAttachRepository(Email email) {
        for(EmailAttach emailAttach : email.getEmailAttach()) {
            emailAttach.setEmail(email);
            emailAttachRepository.save(emailAttach);
        }
    }

    private MimeMessage assignedEmail(Email email) throws MessagingException {
        String[] cc = new String[0];
        String[] bcc = new String[0];

        String[] myCcList = new String[0];
        String[] myBccList = new String[0];

        if(!StringUtils.isEmpty(email.getEmailCc())) {
            cc = email.getEmailCc().trim().split(";");
            myCcList = new String[cc.length];
        }
        if(!StringUtils.isEmpty(email.getEmailBcc())) {
            bcc = email.getEmailBcc().trim().split(";");
            myBccList = new String[bcc.length];
        }

        for( int i = 0; i < cc.length; i++ ) {
            myCcList[i] = cc[i];
        }
        for( int i = 0; i < bcc.length; i++ ) {
            myBccList[i] = bcc[i];
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, ENCODING);
        helper.setFrom(email.getEmailFrom());
        helper.setTo(email.getEmailTo());
        helper.setSubject(email.getSubject());

        if(!StringUtils.isEmpty(email.getText()))
            helper.setText(email.getText(), true);

        if(!StringUtils.isEmpty(myCcList))
            helper.setCc(myCcList);

        if(!StringUtils.isEmpty(myBccList))
            helper.setBcc(myBccList);

        return message;
    }

    private String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return "Anonymous";
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
