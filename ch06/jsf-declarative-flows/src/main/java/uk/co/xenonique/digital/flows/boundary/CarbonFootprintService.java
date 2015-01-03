package uk.co.xenonique.digital.flows.boundary;

import uk.co.xenonique.digital.flows.entity.CarbonFootprint;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type CarbonFootprintService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class CarbonFootprintService {
    @PersistenceContext(unitName = "productFlowDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(CarbonFootprint user) {
        entityManager.persist(user);
    }

    public void update(CarbonFootprint user) {
        CarbonFootprint userUpdated = entityManager.merge(user);
        entityManager.persist(userUpdated);
    }

    public void delete(CarbonFootprint user) {
        entityManager.remove(user);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<CarbonFootprint> findAll() {
        Query query = entityManager.createNamedQuery(
                "CarbonFootprint.findAll");
        return query.getResultList();
    }

    public List<CarbonFootprint> findById(Long id) {
        Query query = entityManager.createNamedQuery(
                "CarbonFootprint.findById").setParameter("id", id);
        return query.getResultList();
    }
}


