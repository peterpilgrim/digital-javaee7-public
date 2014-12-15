package uk.co.xenonique.digital.product.control;

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.boundary.PromotionService;
import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.Campaign;
import uk.co.xenonique.digital.product.entity.Promotion;
import uk.co.xenonique.digital.product.entity.UserProfile;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static uk.co.xenonique.digital.product.utils.AppConsts.LOGIN_KEY;

/**
 * The type CampaignController
 *
 * @author Peter Pilgrim
 */
@Named("promotionController")
@FlowScoped("promotion")
public class PromotionController implements Serializable {

    private int campaignId;
    private int promotionId;
    private Campaign  campaign;
    private Promotion promotion;

    @Inject
    private CampaignService campaignService;
    @Inject
    private PromotionService promotionService;

    @Inject
    private UserProfileService userService;

    public PromotionController() {
        promotion = new Promotion();
    }

    public String getFlowScopeIdentifier() {
        return FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlow().getId();
    }


    public Set<Promotion> retrievePromotionsByCampaignById( int campaignId) {
        campaign = campaignService.findById(campaignId).get(0);
        return campaign.getPromotions();
    }

    public Promotion retrievePromotionById( int promotionId) {
        promotion = promotionService.findById(promotionId).get(0);
        campaign = promotion.getCampaign();
        return promotion;
    }


    // Actions

    private void updateCreationUser() {
        String userKey =  (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LOGIN_KEY);
        List<UserProfile> users = userService.findByUsername(userKey);
        campaign.setCreationUser(users.get(0));
    }

    public String createPromotion() {
        updateCreationUser();
        campaign.getPromotions().add(promotion);
        promotion.setCampaign(campaign);
        promotionService.add(promotion);
        promotion = new Promotion();
        promotion.setCampaign(campaign);
        return "promotion-list.xthml?faces-redirect=true";
    }

    public void retrieveById() {
        if (promotionId <= 0 || campaignId < 0 ) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }
        List<Campaign> campaigns = campaignService.findById(campaignId);
        this.campaign = campaigns.get(0);
        List<Promotion> promotions = promotionService.findById(promotionId);
        this.promotion = promotions.get(0);
    }

    public String editPromotion() {
        updateCreationUser();
        campaignService.update(campaign);
        promotionService.update(promotion);
        promotion = new Promotion();
        promotion.setCampaign(campaign);
        return "promotion.xthml?faces-redirect=true";
    }

    public String removeCampaign() {
        updateCreationUser();
        campaign.getPromotions().remove(promotion);
        promotion.setCampaign(null);
        promotionService.delete(promotion);
        promotion = new Promotion();
        promotion.setCampaign(campaign);
        return "promotion.xthml?faces-redirect=true";
    }

    public String cancel() {
        return "promotion.xhtml?faces-redirect=true";
    }

    public String finish() {
        return "/campaign-return.xhtml?faces-redirect=true";
    }

    public String goHome() {
        return "/index.xhtml?faces-redirect=true";
    }



    // Getter and setters


    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
