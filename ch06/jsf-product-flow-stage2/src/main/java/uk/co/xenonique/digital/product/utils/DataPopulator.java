package uk.co.xenonique.digital.product.utils;

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.Campaign;
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

    public static final String DEFAULT_PASSWORD = "digital";
    @Inject
    UserProfileService userService;
    @Inject
    CampaignService campaignService;

    @PostConstruct
    public void populate() {
        UserRole userRole = new UserRole("user");
        UserRole managerRole = new UserRole("manager");

        List<UserProfile> users = Arrays.asList(
            new UserProfile("user@products.com", DEFAULT_PASSWORD, userRole),
            new UserProfile("test@products.com", DEFAULT_PASSWORD, userRole),
            new UserProfile("developer@products.com", DEFAULT_PASSWORD, userRole),
            new UserProfile("admin@products.com", DEFAULT_PASSWORD, managerRole),
            new UserProfile("admin@products.com", DEFAULT_PASSWORD, managerRole),
            new UserProfile("manager@products.com", DEFAULT_PASSWORD, managerRole)
            );

        for (UserProfile user: users) {
            userService.add(user);
        }

        List<Campaign> campaigns = Arrays.asList(
            new Campaign("Promo 100", "simple promotion"),
            new Campaign("Promo 200", "another promotion"),
            new Campaign("Promo 300", "yet another promotion")
        );
        for (Campaign campaign: campaigns) {
            campaign.setCreationUser(users.get(0));
            campaignService.add(campaign);
        }
    }
}
