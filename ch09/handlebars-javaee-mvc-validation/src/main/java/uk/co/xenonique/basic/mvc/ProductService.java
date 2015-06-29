package uk.co.xenonique.basic.mvc;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ProductService
 *
 * @author Peter Pilgrim
 */
@Stateless
public class ProductService {

    @PersistenceContext(unitName = "HandlebarsMvcDemo")
    private EntityManager entityManager;

    public void saveProduct(Product product) {
        System.out.println("**** saveProduct() product="+product);
        entityManager.persist(product);
    }

    public void updateProduct(Product product) {
        final Product productToBeUpdated = entityManager.merge(product);
        entityManager.persist(productToBeUpdated);
    }

    public void removeProduct(Product product) {
        final Product productToBeRemoved = entityManager.merge(product);
        entityManager.remove(productToBeRemoved);
    }

    public List<Product> findAll() {
        final Query query = entityManager.createNamedQuery("Product.findAll");
        return query.getResultList();
    }

    public List<Product> findById(Integer id) {
        final Query query = entityManager.createNamedQuery("Product.findById").setParameter("id", id);
        return query.getResultList();
    }

}