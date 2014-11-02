package uk.co.xenonique.digital.product.control;

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.entity.Campaign;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

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
