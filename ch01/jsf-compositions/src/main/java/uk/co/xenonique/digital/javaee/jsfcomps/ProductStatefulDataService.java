package uk.co.xenonique.digital.javaee.jsfcomps;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class ProductStatefulDataService {

    @PersistenceContext(unitName = "applicationDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void addProduct(Product product) {
        entityManager.persist(product);
    }

    public void updateProduct(Product product) {
        Product productToBeUpdated = entityManager.merge(product);
        entityManager.persist(productToBeUpdated);
    }

    public void deleteProduct(Product product) {
        entityManager.remove(product);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<Product> findAll() {
        Query query = entityManager.createNamedQuery(
            "Product.findAll");
        return query.getResultList();
    }

}

