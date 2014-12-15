package uk.co.xenonique.digital.product.boundary;

import uk.co.xenonique.digital.product.entity.UserRole;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type UserRoleService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class UserRoleService {
    @PersistenceContext(unitName = "mysqlProductFlowDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(UserRole role) {
        System.out.printf("+++++ BEFORE +++++ UserRoleService.add() role=%s, entityManager=%s\n", role, entityManager);
        entityManager.persist(role);
        entityManager.flush();
        System.out.printf("+++++ AFTER  +++++ UserRoleService.add() role=%s, entityManager=%s\n", role, entityManager);
    }

    public void update(UserRole role) {
        UserRole userUpdated = entityManager.merge(role);
        entityManager.persist(userUpdated);
    }

    public void delete(UserRole role) {
        entityManager.remove(role);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<UserRole> findAll() {
        Query query = entityManager.createNamedQuery(
            "UserRole.findAll");
        return query.getResultList();
    }

    public List<UserRole> findById(Integer id) {
        Query query = entityManager.createNamedQuery(
            "UserRole.findById").setParameter("id", id);
        return query.getResultList();
    }

    public List<UserRole> findByName(String name) {
        Query query = entityManager.createNamedQuery(
                "UserRole.findByName").setParameter("name", name);
        return query.getResultList();
    }
}

