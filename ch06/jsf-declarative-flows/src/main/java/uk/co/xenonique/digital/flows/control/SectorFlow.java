package uk.co.xenonique.digital.flows.control;

import uk.co.xenonique.digital.flows.entity.CarbonFootprint;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type SectionFlowController
 *
 * @author Peter Pilgrim
 *
 */
@Named()
@FlowScoped("sector-flow")
public class SectorFlow implements Serializable{

    private CarbonFootprint carbonFootprint = new CarbonFootprint();

    public String gotoEndFlow() {
        return "/endflow.xhtml";
    }

    public String debugClassName() {
        return this.getClass().getSimpleName();
    }

    // Getters and setters

    public CarbonFootprint getCarbonFootprint() {
        return carbonFootprint;
    }

    public void setCarbonFootprint(CarbonFootprint carbonFootprint) {
        this.carbonFootprint = carbonFootprint;
    }
}
