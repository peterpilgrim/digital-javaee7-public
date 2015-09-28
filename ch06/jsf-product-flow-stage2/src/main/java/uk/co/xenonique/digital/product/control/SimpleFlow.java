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

package uk.co.xenonique.digital.product.control;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type SimpleFlow
 *
 * @author Peter Pilgrim
 */
@Named("simpleFlowController")
@FlowScoped("simple")
public class SimpleFlow implements Serializable{

    private final static String DELIMITER = " ++> ";
    private String location;
    private StringBuilder breadcrumb = new StringBuilder();

    public String getFlowScopeIdentifier() {
        return FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlow().getId();
    }

    private void appendCrumb( String e) {
        if (breadcrumb.length() > 0) {
            breadcrumb.append(DELIMITER);
        }
        breadcrumb.append(e);
    }
    
    public String beginFlow() {
        appendCrumb("BEGIN");
        return "/simple/page1";
    }

    public String page1() {
        appendCrumb("Page 1");
        return "/simple/page2";
    }

    public String page2() {
        appendCrumb("Page 2");
        return "/simple/page3";
    }

    public String endFlow() {
        appendCrumb("END");
        return "/simple-return";
    }

    public String goHome() {
        appendCrumb("HOME");
        return "/index.xhtml";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StringBuilder getBreadcrumb() {
        return breadcrumb;
    }
}
