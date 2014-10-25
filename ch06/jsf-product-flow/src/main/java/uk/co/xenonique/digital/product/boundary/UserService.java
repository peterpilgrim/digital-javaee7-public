package uk.co.xenonique.digital.product.boundary;

import uk.co.xenonique.digital.product.entity.User;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type UserService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class UserService {
    @PersistenceContext(unitName = "productFlowDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(User user) {
        entityManager.persist(user);
    }

    public void update(User user) {
        User userUpdated = entityManager.merge(user);
        entityManager.persist(userUpdated);
    }

    public void delete(User user) {
        entityManager.remove(user);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<User> findAll() {
        Query query = entityManager.createNamedQuery(
            "User.findAll");
        return query.getResultList();
    }

    public List<User> findById(Integer id) {
        Query query = entityManager.createNamedQuery(
            "User.findById").setParameter("id", id);
        return query.getResultList();
    }

    public List<User> findById(String username) {
        Query query = entityManager.createNamedQuery(
                "User.findByUsername").setParameter("user", username);
        return query.getResultList();
    }
}

