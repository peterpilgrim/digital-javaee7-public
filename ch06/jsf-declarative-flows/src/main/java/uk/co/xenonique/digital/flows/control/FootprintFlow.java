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
            final Map<Object,Object> flowMap = FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlowScope();
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
