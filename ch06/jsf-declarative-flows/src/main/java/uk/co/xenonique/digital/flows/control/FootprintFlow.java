package uk.co.xenonique.digital.flows.control;

import uk.co.xenonique.digital.flows.entity.CarbonFootprint;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type CarbonFootprintController
 *
 * @author Peter Pilgrim
 */
@Named
@FlowScoped("footprint-flow")
public class FootprintFlow implements Serializable {

    private CarbonFootprint footprint = new CarbonFootprint();

    public FootprintFlow() {
    }

    public String exitFromFootprintFlow() {
        return "/endflow.xhtml";
    }

    // Getters and setters

    public String debugClassName() {
        return this.getClass().getSimpleName();
    }

    public CarbonFootprint getFootprint() {
        return footprint;
    }

    public void setFootprint(CarbonFootprint footprint) {
        this.footprint = footprint;
    }
}
