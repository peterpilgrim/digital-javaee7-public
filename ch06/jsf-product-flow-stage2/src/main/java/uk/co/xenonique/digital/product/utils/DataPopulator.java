package uk.co.xenonique.digital.product.utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * The type DataPopulator
 *
 * @author Peter Pilgrim
 */
@Singleton
@Startup
public class DataPopulator {

    @Inject
    private ExtendedPersistenceLoaderBean setupBean;

    @PostConstruct
    public void populate()
    {
        setupBean.createData();
    }
}
