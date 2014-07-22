package uk.co.xenonique.digital;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ContactDetailService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class ContactDetailService {
    @PersistenceContext(unitName = "applicationDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(ContactDetail contactDetail) {
        entityManager.persist(contactDetail);
    }

    public void update(ContactDetail contactDetail) {
        ContactDetail contactDetailUpdated
                = entityManager.merge(contactDetail);
        entityManager.persist(contactDetailUpdated);
    }

    public void delete(ContactDetail contactDetail) {
        entityManager.remove(contactDetail);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<ContactDetail> findAll() {
        Query query = entityManager.createNamedQuery(
            "ContactDetail.findAll");
        return query.getResultList();
    }

    public List<ContactDetail> findById(Integer id) {
        Query query = entityManager.createNamedQuery(
            "ContactDetail.findById").setParameter("id", id);
        return query.getResultList();
    }
}

