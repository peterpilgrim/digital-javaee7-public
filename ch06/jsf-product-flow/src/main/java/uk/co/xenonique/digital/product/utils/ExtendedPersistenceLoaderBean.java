package uk.co.xenonique.digital.product.utils;

import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.UserProfile;
import uk.co.xenonique.digital.product.entity.UserRole;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * The type ExtendedPersistenceLoaderBean
 *
 * @author Peter Pilgrim
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ExtendedPersistenceLoaderBean {

    public static final String DEFAULT_TEST_PASSWORD = "digital";

    @Inject
    UserProfileService service;

    public void loadData() {
        UserRole userRole = new UserRole("user");
        UserRole managerRole = new UserRole("manager");


        System.out.printf("***** ExtendedPersistenceLoaderBean  service=%s\n", service);

        List<UserProfile> users = Arrays.asList(
                new UserProfile("user@products.com", DEFAULT_TEST_PASSWORD, userRole),
                new UserProfile("test@products.com", "digital", userRole),
                new UserProfile("developer@products.com", "digital", userRole),
                new UserProfile("admin@products.com", "digital", managerRole),
                new UserProfile("admin@products.com", "digital", managerRole),
                new UserProfile("manager@products.com", "digital", managerRole)
        );

        for (UserProfile user: users) {
            service.add(user);
        }
    }

}