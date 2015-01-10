package uk.co.xenonique.digital.flows.utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * The type EagerInitialiser
 *
 * @author Peter Pilgrim
 */
@Singleton
@Startup
public class EagerInitializer {
    @Inject
    private UtilityHelper utilityHelper;

    @PostConstruct
    public void initialise() {
        // Force application scoped to be initialised with a singleton startup EJB
        // https://rmannibucau.wordpress.com/2012/12/11/ensure-some-applicationscoped-beans-are-eagerly-initialized-with-javaee/
        System.out.printf("******** EagerInitializer.initialise()  utilityHelper=%s\n", utilityHelper);
        utilityHelper.toString();
    }
}