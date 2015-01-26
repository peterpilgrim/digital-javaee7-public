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
        UserRole userRole = new UserRole("user");
        UserRole managerRole = new UserRole("manager");

        System.out.printf("***** ExtendedPersistenceLoaderBean  service=%s\n", service);

        List<UserProfile> users = Arrays.asList(
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