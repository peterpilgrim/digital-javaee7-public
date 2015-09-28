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

package uk.co.xenonique.digital;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Calendar;

/**
 * The type PopulateDataService
 *
 * @author Peter Pilgrim
 */
@Singleton
@Startup
public class PopulateDataService {

    @EJB
    ContactDetailService contactDetailService;

    @PostConstruct
    public void populate() {
        final Calendar cal = Calendar.getInstance();

        final ContactDetail contact1 = new ContactDetail();
        contact1.setAllowEmails(true);
        contact1.setTitle("Mr");
        contact1.setEmail("tim.cook@apple.com");
        contact1.setFirstName("Timothy");
        contact1.setMiddleName("Donald");
        contact1.setLastName("Cook");
        cal.set(1960,1,11);
        contact1.setDob(cal.getTime());
        contactDetailService.add(contact1);

        final ContactDetail contact2 = new ContactDetail();
        contact2.setAllowEmails(false);
        contact2.setTitle("Ms");
        contact2.setEmail("oprah.winfrey@harpo.com");
        contact2.setFirstName("Oprah");
        contact2.setMiddleName("Gail");
        contact2.setLastName("Winfrey");
        cal.set(1954,1,29);
        contact2.setDob(cal.getTime());
        contactDetailService.add(contact2);

        final ContactDetail contact3 = new ContactDetail();
        contact3.setAllowEmails(false);
        contact3.setTitle("Ms");
        contact3.setEmail("cilla.black@liverpool.com");
        contact3.setFirstName("Priscilla");
        contact3.setMiddleName("María Verónica");
        contact3.setLastName("White");
        cal.set(1943,5,27);
        contact3.setDob(cal.getTime());
        contactDetailService.add(contact3);

        final ContactDetail contact4 = new ContactDetail();
        contact4.setAllowEmails(true);
        contact4.setTitle("Mr");
        contact4.setEmail("david.oyelowo@bafta.com");
        contact4.setFirstName("David");
        contact4.setMiddleName("Oyetokunbo");
        contact4.setLastName("Oyelowo");
        cal.set(1976,4,1);
        contact4.setDob(cal.getTime());
        contactDetailService.add(contact4);

    }
}
