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