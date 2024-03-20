package dev.jefferson.email.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.jefferson.email.dtos.EmailDTO;
import dev.jefferson.email.enuns.StatusEmail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TABLE_EMAIL")
public class Email implements Serializable {
    private static final long serialVersionUID = -6407228428249221219L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_EMAIL")
    private UUID idEmail;
    @Column(name = "OWNER_NAME")
    private String ownerRef;
    @Column(name = "EMAIL_TO")
    private String emailTo;
    @Column(name = "EMAIL_FROM")
    private String emailFrom;
    @Column(name = "EMAIL_CC")
    private String emailCc;
    @Column(name = "EMAIL_BCC")
    private String emailBcc;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "TEXT")
    private String text;
    @Column(name = "SEND_DATE_EMAIL")
    private LocalDateTime sendDateEmail;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_EMAIL")
    private StatusEmail statusEmail;
    @OneToMany(mappedBy = "email", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EmailAttach> emailAttach = new ArrayList<EmailAttach>();
    @OneToMany(mappedBy = "email", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EmailLog> emailLog = new ArrayList<EmailLog>();
    @Transient
    private String templateName;

    public Email(EmailDTO emailDTO) {
        BeanUtils.copyProperties(emailDTO, this);
    }
}
