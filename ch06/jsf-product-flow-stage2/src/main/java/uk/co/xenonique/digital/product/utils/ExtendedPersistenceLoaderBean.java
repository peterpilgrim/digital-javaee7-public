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

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.boundary.PromotionService;
import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.boundary.UserRoleService;
import uk.co.xenonique.digital.product.entity.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.InputStream;
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

    public static final String DEFAULT_PASSWORD = "digital";

    public final static String ADMIN_USERNAME = "admin@products.com";
    public static final String NORMAL_USERNAME = "user@products.com";

    @Inject
    UserRoleService userRoleService;
    @Inject
    UserProfileService userProfileService;
    @Inject
    CampaignService campaignService;
    @Inject
    PromotionService promotionService;


    @PostConstruct
    public void init() {
        System.out.printf("***** %s.init()\n", this.getClass().getName());
    }

    @PreDestroy
    public void destroy() {
        System.out.printf("***** %s.destroy()\n", this.getClass().getName());
    }


    public void createData() {
        UserRole userRole = new UserRole("user");
        UserRole managerRole = new UserRole("manager");

        final List<UserRole> roles = Arrays.asList(userRole, managerRole);
        for (UserRole role: roles) {
            userRoleService.add(role);
        }

        final List<UserProfile> users = Arrays.asList(
                new UserProfile(NORMAL_USERNAME, DEFAULT_PASSWORD, userRole),
                new UserProfile("test@products.com", DEFAULT_PASSWORD, userRole),
                new UserProfile("developer@products.com", DEFAULT_PASSWORD, userRole),
                new UserProfile(ADMIN_USERNAME, DEFAULT_PASSWORD, managerRole),
                new UserProfile("manager@products.com", DEFAULT_PASSWORD, managerRole)
        );


        for (UserProfile user: users) {
            userProfileService.add(user);
        }

        final List<Campaign> campaigns = Arrays.asList(
                new Campaign("Campaign 100", "simple marketing"),
                new Campaign("Advertising 200", "another sales campaign"),
                new Campaign("Marketing 300", "yet another direct marketing event")
        );

        int base = 1001;
        for (Campaign campaign: campaigns) {
            campaign.setCreationUser(userProfileService.findByUsername(NORMAL_USERNAME).get(0));
            campaignService.add(campaign);
            System.out.printf("***** campaign=%s\n", campaign );
        }

//        List<Campaign> campaigns2 = campaignService.findAll();
        int index=0;
        for (Campaign campaign: campaigns) {
            for (int k=0; k<3+index; ++k) {
                final int promoIndex = k + base;
                final Promotion promotion = new Promotion("promo headline "+promoIndex, "promo description "+promoIndex);
                promotionService.add(promotion);
                campaign.getPromotions().add(promotion);
                promotion.setCampaign(campaign);

                if ( index==0)  {
                    final Approver approver = new Approver();
                    approver.setUser( userProfileService.findByUsername(ADMIN_USERNAME).get(0));
                    approver.setComment("This promotion has been pre-approved.");
                    promotion.getApprovers().add(approver);
                    promotionService.update(promotion);
                }
            }
            base += 1000;
            campaignService.update(campaign);
            System.out.printf("+++++ campaign=%s\n", campaign );
            ++index;
        }
    }

}
