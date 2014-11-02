package uk.co.xenonique.digital.product.boundary;

import uk.co.xenonique.digital.product.entity.Campaign;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type CampaignService
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class CampaignService {
    @PersistenceContext(unitName = "productFlowDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(Campaign obj) {
        entityManager.persist(obj);
    }

    public void update(Campaign obj) {
        Campaign objUpdated = entityManager.merge(obj);
        entityManager.persist(objUpdated);
    }

    public void delete(Campaign obj) {
        entityManager.remove(obj);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<Campaign> findAll() {
        Query query = entityManager.createNamedQuery(
                "Campaign.findAll");
        return query.getResultList();
    }

    public List<Campaign> findById(Integer id) {
        Query query = entityManager.createNamedQuery(
                "Campaign.findById").setParameter("id", id);
        return query.getResultList();
    }

    public List<Campaign> findByUsername(String username) {
        Query query = entityManager.createNamedQuery(
                "Campaign.findByUsername").setParameter("username", username);
        return query.getResultList();
    }

}
