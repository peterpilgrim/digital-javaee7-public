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

import uk.co.xenonique.digital.flows.boundary.CarbonFootprintService;
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

    @Inject
    CarbonFootprintService service;

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

    public String saveFootprintRecord() {
        service.add(footprint);
        return "sector-flow-1c.xhtml";
    }

    // Getters and setters

    public CarbonFootprint getFootprint() {
        return footprint;
    }

    public void setFootprint(CarbonFootprint footprint) {
        this.footprint = footprint;
    }
}
