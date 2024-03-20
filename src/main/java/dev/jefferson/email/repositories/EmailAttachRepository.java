package dev.jefferson.email.repositories;

import dev.jefferson.email.entities.EmailAttach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailAttachRepository extends JpaRepository<EmailAttach, UUID> {
}
