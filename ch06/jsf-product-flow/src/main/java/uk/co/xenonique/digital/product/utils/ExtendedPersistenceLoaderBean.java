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

import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.UserProfile;
import uk.co.xenonique.digital.product.entity.UserRole;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * The type ExtendedPersistenceLoaderBean
 *
 * @author Peter Pilgrim
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@javax.transaction.TransactionScoped
public class ExtendedPersistenceLoaderBean implements Serializable {

    public static final String DEFAULT_PWD = "digital";

    @Inject
    UserProfileService service;

    @PostConstruct
    public void init() {
        System.out.printf("***** %s.init()\n", this.getClass().getName());
    }

    @PreDestroy
    public void destroy() {
        System.out.printf("***** %s.destroy()\n", this.getClass().getName());
    }

    public void loadData() {
        final UserRole userRole = new UserRole("user");
        final UserRole managerRole = new UserRole("manager");

        System.out.printf("***** ExtendedPersistenceLoaderBean  service=%s\n", service);

        final List<UserProfile> users = Arrays.asList(
            new UserProfile("user@products.com", DEFAULT_PWD, userRole),
            new UserProfile("test@products.com", DEFAULT_PWD, userRole),
            new UserProfile("developer@products.com", DEFAULT_PWD, userRole),
            new UserProfile("admin@products.com", DEFAULT_PWD, managerRole),
            new UserProfile("admin@products.com", DEFAULT_PWD, managerRole),
            new UserProfile("manager@products.com", DEFAULT_PWD, managerRole)
        );

        for (UserProfile user: users) {
            service.add(user);
        }
    }

}