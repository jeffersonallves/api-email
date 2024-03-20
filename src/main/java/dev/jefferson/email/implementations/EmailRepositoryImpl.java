package dev.jefferson.email.implementations;

import dev.jefferson.email.entities.EmailLog;
import dev.jefferson.email.repositories.EmailRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class EmailRepositoryImpl implements EmailRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    public Page<EmailLog> findLogEmailByIdAndDate(String emailLogDate, UUID idEmail, Pageable pageable) throws Exception {

        StringBuilder sb = new StringBuilder();

        try {
            sb.append("SELECT TBL ");
            sb.append("        FROM Email TB ");
            sb.append("INNER JOIN EmailLog TBL ON TB.idEmail = TBL.email.idEmail ");
            sb.append("WHERE (:idEmail IS NULL OR TB.idEmail = :idEmail) ");
            sb.append("     AND (:emailLogDate IS NULL OR TBL.emailLogDate >= to_timestamp(:emailLogDate,'DD/MM/YYYY HH24:MI:SS')) ");
            sb.append("ORDER BY TBL.emailLogDate DESC ");

            TypedQuery<EmailLog> typedQuery = em.createQuery(sb.toString(), EmailLog.class);
            typedQuery.setParameter("idEmail", idEmail);
            typedQuery.setParameter("emailLogDate", emailLogDate);
            typedQuery.setFirstResult((int)pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());

            return new PageImpl<>(typedQuery.getResultList());
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            this.em.close();
        }

    }
}
