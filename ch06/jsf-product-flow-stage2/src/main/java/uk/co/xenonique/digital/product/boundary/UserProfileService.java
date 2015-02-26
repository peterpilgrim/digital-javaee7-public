package uk.co.xenonique.digital.product.boundary;

import uk.co.xenonique.digital.product.entity.UserProfile;

import javax.ejb.Remove;
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
public class UserProfileService {
    @PersistenceContext(unitName = "productFlow", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(UserProfile user) {
        System.out.printf("+++++ BEFORE +++++ UserProfileService.add() user=%s, entityManager=%s\n", user, entityManager);
        entityManager.persist(user);
        System.out.printf("+++++ AFTER  +++++ UserProfileService.add() user=%s, entityManager=%s\n", user, entityManager);
    }

    public void update(UserProfile user) {
        final UserProfile userUpdated = entityManager.merge(user);
        entityManager.persist(userUpdated);
    }

    public void delete(UserProfile user) {
        entityManager.remove(user);
    }

    @Remove
    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<UserProfile> findAll() {
        final Query query = entityManager.createNamedQuery(
            "UserProfile.findAll");
        return query.getResultList();
    }

    public List<UserProfile> findById(Long id) {
        final Query query = entityManager.createNamedQuery(
            "UserProfile.findById").setParameter("id", id);
        return query.getResultList();
    }

    public List<UserProfile> findByUsername(String username) {
        final Query query = entityManager.createNamedQuery(
                "UserProfile.findByUsername").setParameter("username", username);
        return query.getResultList();
    }
}

