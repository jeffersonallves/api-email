package dev.jefferson.email.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TABLE_EMAIL_LOG")
public class EmailLog implements Serializable {
    private static final long serialVersionUID = -7819774632468751101L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "EMAIL_LOG_ID")
    private UUID idEmailLog;
    @Column(name = "EMAIL_LOG_DESC")
    private String emailLogDesc;
    @Column(name = "EMAIL_LOG_DATE")
    private LocalDateTime emailLogDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMAIL")
    @JsonIgnore
    private Email email;
}
