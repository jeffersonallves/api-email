package dev.jefferson.email.repositories;

import dev.jefferson.email.entities.EmailLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmailRepositoryCustom {
    Page<EmailLog> findLogEmailByIdAndDate(String emailLogDate, UUID idEmail, Pageable pageable) throws Exception;
}
