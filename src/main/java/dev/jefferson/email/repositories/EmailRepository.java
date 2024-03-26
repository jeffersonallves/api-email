package dev.jefferson.email.repositories;

import dev.jefferson.email.entities.Email;
import dev.jefferson.email.enuns.StatusEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EmailRepository extends JpaRepository<Email, UUID> {
    @Query("from Email e where e.statusEmail = :statusEmail and e.sendDateEmail > to_date(:sendDateEmail, 'DD/MM/YYYY HH24:MI:SS') order by e.sendDateEmail desc")
    List<Email> findByStatusEmailAndDate(@Param("statusEmail") StatusEmail statusEmail,
                                         @Param("sendDateEmail") String sendDateEmail);

    Page<Email> findByStatusEmail(StatusEmail statusEmail, Pageable pageable);
    Page<Email> findBySendDateEmailBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<Email> findBySendDateEmailAfter(LocalDateTime start, Pageable pageable);

    @Query("from Email e " +
            "inner join e.emailAttach att on e.idEmail = att.email.idEmail " +
            "where e.statusEmail = :statusEmail and e.sendDateEmail > to_date(:sendDateEmail, 'DD/MM/YYYY HH24:MI:SS') order by e.sendDateEmail desc")
    List<Email> findByStatusEmailWithAttach(@Param("statusEmail") StatusEmail statusEmail);
}
