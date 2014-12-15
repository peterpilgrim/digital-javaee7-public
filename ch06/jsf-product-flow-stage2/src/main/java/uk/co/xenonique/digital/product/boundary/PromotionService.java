package uk.co.xenonique.digital.product.boundary;

import uk.co.xenonique.digital.product.entity.Promotion;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type PromotionService
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class PromotionService {
    @PersistenceContext(unitName = "mysqlProductFlowDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(Promotion obj) {
        entityManager.persist(obj);
    }

    public void update(Promotion obj) {
        Promotion objUpdated = entityManager.merge(obj);
        entityManager.persist(objUpdated);
    }

    public void delete(Promotion obj) {
        entityManager.remove(obj);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<Promotion> findAll() {
        Query query = entityManager.createNamedQuery(
                "Promotion.findAll");
        return query.getResultList();
    }

    public List<Promotion> findById(Long id) {
        Query query = entityManager.createNamedQuery(
                "Promotion.findById").setParameter("id", id);
        return query.getResultList();
    }

}
