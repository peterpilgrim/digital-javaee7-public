/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015,2016 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
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

package uk.co.xenonique.digital.cdi;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

/**
 * The type SecureDomain
 *
 * @author Peter Pilgrim
 */
@RequestScoped
public class SecureDomain {

    private String customerId;

    @PostConstruct
    public void initialise() {
        customerId = "client/customer/"+Integer.toHexString( (int)(Math.random() * 1e10 ));
        System.out.printf("**** %s.initialise() customerId=%s", getClass().getSimpleName(), customerId );
    }

    public String getDomain() {
        return "www.xenonique.co.uk/"+customerId;
    }
}
