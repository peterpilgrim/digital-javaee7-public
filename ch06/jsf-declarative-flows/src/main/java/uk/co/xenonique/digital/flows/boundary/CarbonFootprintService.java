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
    @PersistenceContext(unitName = "declarativeFlowDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(CarbonFootprint footprint) {
        entityManager.persist(footprint);
    }

    public void update(CarbonFootprint footprint) {
        CarbonFootprint footprintUpdated = entityManager.merge(footprint);
        entityManager.persist(footprintUpdated);
    }

    public void delete(CarbonFootprint footprint) {
        entityManager.remove(footprint);
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


