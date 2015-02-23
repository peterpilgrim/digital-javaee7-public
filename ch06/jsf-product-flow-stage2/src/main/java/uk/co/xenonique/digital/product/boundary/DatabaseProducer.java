package uk.co.xenonique.digital.product.boundary;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * The type DatabaseProducer
 *
 * @author Peter Pilgrim
 */
public class DatabaseProducer {

    @Produces
    @PersistenceContext(unitName = "productFlow", type = PersistenceContextType.EXTENDED)
    @ProductFlowDatabase
    private EntityManager em;
}
