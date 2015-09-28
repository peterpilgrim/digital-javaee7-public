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

//import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
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

    @Inject
    ContactDetailService contactDetailService;

    @PostConstruct
    public void populate() {
        Calendar cal = Calendar.getInstance();

        ContactDetail contact1 = new ContactDetail();
        contact1.setAllowEmails(true);
        contact1.setTitle("Mr");
        contact1.setEmail("steve.jobs@apple.com");
        contact1.setFirstName("Steven");
        contact1.setMiddleName("Paul");
        contact1.setLastName("Jobs");
        cal.set(1955,2-1,24);
        contact1.setDob(cal.getTime());
        contactDetailService.add(contact1);

        ContactDetail contact2 = new ContactDetail();
        contact2.setAllowEmails(false);
        contact2.setTitle("Ms");
        contact2.setEmail("oprah.winfrey@harpo.com");
        contact2.setFirstName("Oprah");
        contact2.setMiddleName("Gail");
        contact2.setLastName("Winfrey");
        cal.set(1954,1-1,29);
        contact2.setDob(cal.getTime());
        contactDetailService.add(contact2);

        ContactDetail contact3 = new ContactDetail();
        contact3.setAllowEmails(false);
        contact3.setTitle("Mr");
        contact3.setEmail("chris.hughton@footballmanager.com");
        contact3.setFirstName("Christopher");
        contact3.setMiddleName("William Gerard ");
        contact3.setLastName("Hughton");
        cal.set(1958,12-1,11);
        contact3.setDob(cal.getTime());
        contactDetailService.add(contact3);

        ContactDetail contact4 = new ContactDetail();
        contact4.setAllowEmails(true);
        contact4.setTitle("Ms");
        contact4.setEmail("Eniola.Aluko@chelsealadies.com");
        contact4.setFirstName("Eniola");
        contact4.setMiddleName("");
        contact4.setLastName("Aluko");
        cal.set(1987,2-1,21);
        contact4.setDob(cal.getTime());
        contactDetailService.add(contact4);

        ContactDetail contact5 = new ContactDetail();
        contact5.setAllowEmails(true);
        contact5.setTitle("Mrs");
        contact5.setEmail("thandie.newton@bafta.com");
        contact5.setFirstName("Thandiwe");
        contact5.setMiddleName("Melanie \"Thandie\"");
        contact5.setLastName("Newton");
        cal.set(1972,11-1,6);
        contact5.setDob(cal.getTime());
        contactDetailService.add(contact5);


        ContactDetail contact6 = new ContactDetail();
        contact6.setAllowEmails(true);
        contact6.setTitle("Mrs");
        contact6.setEmail("hillary.clinton@senate.gov");
        contact6.setFirstName("Hillary");
        contact6.setMiddleName("Diane Rodham");
        contact6.setLastName("Clinton");
        cal.set(1946,10-1,26);
        contact6.setDob(cal.getTime());
        contactDetailService.add(contact6);
    }
}
