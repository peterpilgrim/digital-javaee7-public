package uk.co.xenonique.digital.flows.control;

import uk.co.xenonique.digital.flows.entity.CarbonFootprint;
import uk.co.xenonique.digital.flows.utils.UtilityHelper;

import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type SectionFlowController
 *
 * @author Peter Pilgrim
 *
 */
@Named
@FlowScoped("sector-flow")
public class SectorFlow implements Serializable{

    @Inject
    UtilityHelper utilityHelper;

    private CarbonFootprint footprint = new CarbonFootprint();

    public SectorFlow() {
    }

    @PostConstruct
    public void initialize() {
        System.out.printf("********* utilityHelper=%s\n", utilityHelper);
        footprint.setApplicationId(utilityHelper.getNextApplicationId());
    }

    public String gotoEndFlow() {
        return "/endflow.xhtml";
    }

    public String debugClassName() {
        return this.getClass().getSimpleName();
    }

    // Getters and setters

    public CarbonFootprint getFootprint() {
        return footprint;
    }

    public void setFootprint(CarbonFootprint footprint) {
        this.footprint = footprint;
    }
}
