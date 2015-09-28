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

package uk.co.xenonique.digital.instant.control;

import javax.faces.component.*;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * The type WorkerBannerComponent
 *
 * @author Peter Pilgrim
 */
@FacesComponent("workerBannerComponent")
public class WorkerBannerComponent extends UINamingContainer implements Serializable{

    private NavigatableController controller;

    @Override
    public void encodeAll(FacesContext context) throws IOException {
        if (context == null) {
            throw new NullPointerException("no faces context supplied");
        }
        final String sectionName = (String)getAttributes().get("sectionName");
//        setSectionName(sectionName);
        controller = (NavigatableController)getAttributes().get("controller");
        final SmartNavigation navigation = controller.getNavigation();
        System.out.printf(">>>>> WorkerBannerComponent.encodeAll() navigation=%s\n", navigation);

//        setNavigation(navigation);

        for (NavElement element: navigation.getElements()) {
            if ( sectionName.endsWith( element.getName())) {
                element.setStyle("active");
            }
            else {
                element.setStyle("");
            }
        }
        super.encodeAll(context);
    }

}
