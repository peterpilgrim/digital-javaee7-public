package uk.co.xenonique.digital.instant.boundary;

import uk.co.xenonique.digital.instant.entity.Applicant;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ApplicantService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class ApplicantService {
    @PersistenceContext(unitName = "applicationDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(Applicant applicant) {
        entityManager.persist(applicant);
    }

    public void update(Applicant applicant) {
        Applicant applicantUpdated
                = entityManager.merge(applicant);
        entityManager.persist(applicantUpdated);
    }

    public void delete(Applicant applicant) {
        entityManager.remove(applicant);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<Applicant> findAll() {
        Query query = entityManager.createNamedQuery(
            "Applicant.findAll");
        return query.getResultList();
    }

    public List<Applicant> findById(Integer id) {
        Query query = entityManager.createNamedQuery(
            "Applicant.findById").setParameter("id", id);
        return query.getResultList();
    }
}

