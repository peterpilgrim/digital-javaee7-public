/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

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
