package uk.co.xenonique.digital.product.control;

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.Campaign;
import uk.co.xenonique.digital.product.entity.UserProfile;

import javax.faces.application.FacesMessage;
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

    private int id;
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

    private void updateCreationUser() {
        String userKey =  (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LOGIN_KEY);
        List<UserProfile> users = userService.findByUsername(userKey);
        campaign.setCreationUser(users.get(0));
    }

    public String createCampaign() {
        updateCreationUser();
        campaignService.add(campaign);
        campaign = new Campaign();
        return "campaign.xthml?faces-redirect=true";
    }

    public void retrieveById() {
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
        return "campaign.xthml?faces-redirect=true";
    }

    public String removeCampaign() {
        updateCreationUser();
        campaignService.delete(campaign);
        campaign = new Campaign();
        return "campaign.xthml?faces-redirect=true";
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

}
