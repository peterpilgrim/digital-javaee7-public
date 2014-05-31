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
        cal.set(1955,2,24);
        contact1.setDob(cal.getTime());
        contactDetailService.add(contact1);

        ContactDetail contact2 = new ContactDetail();
        contact2.setAllowEmails(false);
        contact2.setTitle("Ms");
        contact2.setEmail("oprah.winfrey@harpo.com");
        contact2.setFirstName("Oprah");
        contact2.setMiddleName("Gail");
        contact2.setLastName("Winfrey");
        cal.set(1954,1,29);
        contact2.setDob(cal.getTime());
        contactDetailService.add(contact2);

//        DateTime dt = new DateTime();
//        System.out.printf("dt=%s",dt);
    }
}
