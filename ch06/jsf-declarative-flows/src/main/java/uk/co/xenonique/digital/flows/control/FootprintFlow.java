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
@FlowScoped("footprint")
public class FootprintFlow implements Serializable {

    private CarbonFootprint footprint;

    public FootprintFlow() {
        System.out.printf("**** %s.initialize()", getClass().getSimpleName());
        if ( footprint == null) {
            final Map<Object,Object> flowMap = FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlowScope();
            footprint = (CarbonFootprint) flowMap.get("param3Value");
        }
    }

    public String exitOutsideAllFlows() {
        return "/endflow.xhtml";
    }

    public String exitFromFootprintFlow() {
        return "/sector";
    }

    public String updateAndExitToCallingFlow() {
        return "/sector";
    }

    public String gotoPage1() {
        System.out.printf("****** %s.gotoPage1() footprint=%s\n", getClass().getSimpleName(), footprint);
        return "footprint";
    }

    public String gotoPage2() {
        System.out.printf("****** %s.gotoPage2() footprint=%s\n", getClass().getSimpleName(), footprint);
        return "footprint-page-1a";
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
