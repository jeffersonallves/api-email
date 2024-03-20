package dev.jefferson.email.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TABLE_EMAIL_ATTACH")
public class EmailAttach implements Serializable {
    private static final long serialVersionUID = -3776812177510683530L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_EMAIL_ATTACH")
    private UUID idEmailAttach;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMAIL")
    private Email email;
    @Column(name = "ATTACHMENT")
    private byte[] attach;
}
