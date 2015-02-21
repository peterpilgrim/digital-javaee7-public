package uk.co.xenonique.digital.product.boundary;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * The type DatabaseProducer
 *
 * @author Peter Pilgrim
 */
public class DatabaseProducer {

    @Produces
    @PersistenceContext(unitName = "productFlow")
    @ProductFlowDatabase
    private EntityManager em;
}
