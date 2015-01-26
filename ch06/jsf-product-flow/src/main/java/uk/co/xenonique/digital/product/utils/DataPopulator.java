package uk.co.xenonique.digital.product.utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * The type DataPopulator
 *
 * @author Peter Pilgrim                                                                                                                                             n
 */
@Singleton
@Startup
public class DataPopulator {

    @Inject
    ExtendedPersistenceLoaderBean loaderBean;

    @PostConstruct
    public void populate() {
        System.out.printf("***** %s.populate() loaderBean=%s\n", this.getClass().getName(), loaderBean);
        loaderBean.loadData();
    }
}
