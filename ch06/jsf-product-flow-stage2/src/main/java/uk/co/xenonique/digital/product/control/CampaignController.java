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

import static uk.co.xenonique.digital.product.utils.AppConsts.*;

/**
 * The type CampaignController
 *
 * @author Peter Pilgrim
 */
@Named("campaignController")
@FlowScoped("campaign")
public class CampaignController implements Serializable {

    private long id;
    private long promotionId;
    private Campaign  campaign;
    private Promotion promotion;

    @Inject
    private CampaignService campaignService;
    @Inject
    private PromotionService promotionService;
    @Inject
    private UserProfileService userService;

    public CampaignController() {
        campaign = new Campaign();
    }

    public String getFlowScopeIdentifier() {
        return FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlow().getId();
    }


    public List<Campaign> retrieveAllCampaigns() {
        return campaignService.findAll();
    }

    // Actions

    private void updateCreationUser() {
        String userKey =  (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LOGIN_KEY);
        List<UserProfile> users = userService.findByUsername(userKey);
        campaign.setCreationUser(users.get(0));
    }

    public String createCampaign() {
        campaignService.add(campaign);
        updateCreationUser();
        campaignService.update(campaign);
        campaign = new Campaign();
        return "campaign.xhtml?faces-redirect=true";
    }

    public void retrieveByCampaignId() {
        if (id <= 0) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }
        List<Campaign> campaigns = campaignService.findById(id);
        this.campaign = campaigns.get(0);
    }

    public String editCampaign() {
        updateCreationUser();
        campaignService.update(campaign);
        campaign = new Campaign();
        return "campaign.xhtml?faces-redirect=true";
    }

    public String removeCampaign() {
        campaignService.delete(campaign);
        campaign = new Campaign();
        return "campaign.xhtml?faces-redirect=true";
    }

    public Promotion retrievePromotionById( long promotionId) {
        promotion = promotionService.findById(promotionId).get(0);
        campaign = promotion.getCampaign();
        return promotion;
    }


    // Promotions

    public String createPromotion() {
        updateCreationUser();
        campaign.getPromotions().add(promotion);
        promotion.setCampaign(campaign);
        promotionService.add(promotion);
        promotion = new Promotion();
        promotion.setCampaign(campaign);
        return "promotion-list.xhtml?faces-redirect=true";
    }

    public Set<Promotion> retrievePromotionsByCampaignById( long campaignId) {
        campaign = campaignService.findById(campaignId).get(0);
        return campaign.getPromotions();
    }

    public void retrieveByPromotionId() {
        if (promotionId <= 0 || id < 0 ) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }
        List<Campaign> campaigns = campaignService.findById(id);
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
        return "promotion.xhtml?faces-redirect=true";
    }

    public String removePromotion() {
        updateCreationUser();
        campaign.getPromotions().remove(promotion);
        promotion.setCampaign(null);
        promotionService.delete(promotion);
        promotion = new Promotion();
        promotion.setCampaign(campaign);
        return "promotion.xhtml?faces-redirect=true";
    }

    public String cancel() {
        return "campaign.xhtml?faces-redirect=true";
    }

    public String finish() {
        return "/campaign-return.xhtml?faces-redirect=true";
    }

    public String goHome() {
        return "/index.xhtml?faces-redirect=true";
    }


    // Getter and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(long promotionId) {
        this.promotionId = promotionId;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

}
