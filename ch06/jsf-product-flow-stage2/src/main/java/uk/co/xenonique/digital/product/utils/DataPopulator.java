package uk.co.xenonique.digital.product.utils;

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.boundary.PromotionService;
import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.boundary.UserRoleService;
import uk.co.xenonique.digital.product.entity.Campaign;
import uk.co.xenonique.digital.product.entity.Promotion;
import uk.co.xenonique.digital.product.entity.UserProfile;
import uk.co.xenonique.digital.product.entity.UserRole;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
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
    UserRoleService userRoleService;
    @Inject
    UserProfileService userService;
    @Inject
    CampaignService campaignService;
    @Inject
    PromotionService promotionService;

    @PostConstruct
    public void populate()
    {
        UserRole userRole = new UserRole("user");
        UserRole managerRole = new UserRole("manager");

        List<UserRole> roles = Arrays.asList(userRole, managerRole);
        for (UserRole role: roles) {
            userRoleService.add(role);
        }

        List<UserProfile> users = Arrays.asList(
                new UserProfile("user@products.com", DEFAULT_PASSWORD, userRole),
                new UserProfile("test@products.com", DEFAULT_PASSWORD, userRole),
                new UserProfile("developer@products.com", DEFAULT_PASSWORD, userRole),
                new UserProfile("admin@products.com", DEFAULT_PASSWORD, managerRole),
                new UserProfile("manager@products.com", DEFAULT_PASSWORD, managerRole)
        );

        for (UserProfile user: users) {
            System.out.printf("***** user=%s\n", user);
            userService.add(user);
        }

        List<Campaign> campaigns = Arrays.asList(
                new Campaign("Campaign 100", "simple promotion"),
                new Campaign("Advertising 200", "another promotion"),
                new Campaign("Marketing 300", "yet another promotion")
        );

        int base = 1001;
        for (Campaign campaign: campaigns) {
            campaign.setCreationUser(users.get(0));
            campaignService.add(campaign);
            System.out.printf("***** campaign=%s\n", campaign );
        }

//        List<Campaign> campaigns2 = campaignService.findAll();
//        for (Campaign campaign: campaigns) {
//            for (int k=0; k<3; ++k) {
//                final int promoIndex = k + base;
//                Promotion promotion = new Promotion("headline "+promoIndex, "description "+promoIndex);
//                campaign.getPromotions().add(promotion);
//                promotion.setCampaign(campaign);
//            }
//            base += 1000;
//            campaignService.update(campaign);
//            System.out.printf("+++++ campaign=%s\n", campaign );
//        }
    }
}
