package uk.co.xenonique.digital.flows.control;

import uk.co.xenonique.digital.flows.entity.CarbonFootprint;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

/**
 * The type CarbonFootprintController
 *
 * @author Peter Pilgrim
 */
@Named
@FlowScoped("footprint-flow")
public class FootprintFlow implements Serializable {

    private CarbonFootprint footprint;

    public FootprintFlow() {
        if ( footprint == null) {
            Map<Object,Object> flowMap = FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlowScope();
            footprint = (CarbonFootprint) flowMap.get("param3Value");
        }
    }

    public String exitFromFootprintFlow() {
        return "/endflow.xhtml";
    }

    public String gotoPage1() {
        System.out.printf("****** gotoPage1() footprint=%s\n", footprint);
        return "footprint-flow";
    }

    public String gotoPage2() {
        System.out.printf("****** gotoPage2() footprint=%s\n", footprint);
        return "footprint-flow-1a";
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
