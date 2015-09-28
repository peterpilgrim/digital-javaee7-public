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

package uk.co.xenonique.digital;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.Map;

/**
 * The type ExemplarController
 *
 * @author Peter Pilgrim
 */
@Named("examplarController")
@ViewScoped
public class ExemplarController {
    private String city = "London";
    private String country="United Kingdom";

    public String methodOneArg( String alpha ) {
        final Flash flash = FacesContext.getCurrentInstance().
            getExternalContext().getFlash();
        flash.put("result",
            String.format("executed methodOneArg(\"%s\")", alpha ));
        return "examplar-methods-complete?redirect=true";
    }

    public String methodTwoArgs( String alpha, String beta ) {
        final Flash flash = FacesContext.getCurrentInstance().
                getExternalContext().getFlash();
        flash.put("result",
                String.format("executed methodTwoArgs(\"%s\", \"%s\")", alpha, beta ));
        return "examplar-methods-complete?redirect=true";
    }

    public String methodThreeArgs( String alpha, String beta, String gamma ) {
        final Flash flash = FacesContext.getCurrentInstance().
                getExternalContext().getFlash();
        flash.put("result",
                String.format("executed methodThreeArgs(\"%s\", \"%s\", \"%s\")", alpha, beta, gamma ));
        return "examplar-methods-complete?redirect=true";
    }

    public String methodPassingParameters() {
        final Map<String,String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        final String ctaText = params.get("callToActionText");
        final String custType = params.get("customerType");
        final Flash flash = FacesContext.getCurrentInstance().
                getExternalContext().getFlash();
        flash.put("result",
                String.format("executed methodPassingParameters() " +
                        "ctaText=\"%s\", custType=%s", ctaText, custType ));
        return "examplar-methods-complete?redirect=true";
    }

    private String contactName;

    public void attributeListener(ActionEvent event){
        contactName = (String) event.getComponent().getAttributes().get("contactName");
    }

    public String performAction() {
        final Flash flash = FacesContext.getCurrentInstance().
                getExternalContext().getFlash();
        flash.put("result",
                String.format("executed performAction() contactName=\"%s\" ", contactName ));
        return "examplar-methods-complete?redirect=true";
    }

    // Getters and setters ...
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
