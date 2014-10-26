package uk.co.xenonique.digital.product.utils;

import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.UserProfile;
import uk.co.xenonique.digital.product.entity.UserRole;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * The type DataPopulator
 *
 * @author Peter Pilgrim
 */
@Singleton
@Startup
public class DataPopulator {

    public static final String DEFAULT_TEST_PASSWORD = "digital";
    @Inject
    UserProfileService service;

    @PostConstruct
    public void populate() {
        UserRole userRole = new UserRole("user");
        UserRole managerRole = new UserRole("manager");

        List<UserProfile> users = Arrays.asList(
            new UserProfile("user@products.com", DEFAULT_TEST_PASSWORD, userRole),
            new UserProfile("test@products.com","digital", userRole),
            new UserProfile("developer@products.com","digital", userRole),
            new UserProfile("admin@products.com","digital", managerRole),
            new UserProfile("admin@products.com","digital", managerRole),
            new UserProfile("manager@products.com","digital", managerRole)
            );

        for (UserProfile user: users) {
            service.add(user);
        }
    }
}
