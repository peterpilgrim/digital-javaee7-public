package uk.co.xenonique.digital;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ProductStatelessDataService {

    @PersistenceContext(unitName = "applicationDB")
    private EntityManager entityManager;

    public void addProduct(Product product) {
        entityManager.persist(product);
    }

    public void updateProduct(Product product) {
        Product productToBeUpdated = entityManager.merge(product);
        entityManager.persist(productToBeUpdated);
    }

    public void deleteProduct(Product product) {
        Product productToBeUpdated = entityManager.merge(product);
        entityManager.remove(productToBeUpdated);
    }

    public List<Product> findAll() {
        Query query = entityManager.createNamedQuery(
            "Product.findAll");
        return query.getResultList();
    }
}

