package uk.co.xenonique.digital.product.control;

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.Campaign;
import uk.co.xenonique.digital.product.entity.UserProfile;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static uk.co.xenonique.digital.product.utils.AppConsts.*;

/**
 * The type CampaignController
 *
 * @author Peter Pilgrim
 */
@Named("campaignController")
@FlowScoped("campaign")
public class CampaignController implements Serializable {

    private Campaign  campaign;

    @Inject
    private CampaignService campaignService;
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

    public String createCampaign() {
        String userKey =  (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LOGIN_KEY);
        List<UserProfile> users = userService.findByUsername(userKey);
        campaign.setCreationUser(users.get(0));
        campaignService.add(campaign);
        campaign = new Campaign();
        return "campaign.xthml?faces-redirect=true";
    }

    public String cancel() {
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

}