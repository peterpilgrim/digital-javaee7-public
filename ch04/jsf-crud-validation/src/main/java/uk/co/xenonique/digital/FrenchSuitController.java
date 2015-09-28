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
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * The type FrenchSuitController
 *
 * @author Peter Pilgrim
 */
@Named("frenchSuitController")
@ViewScoped
public class FrenchSuitController {
    private String firstName;
    private String lastName;
    private FrenchSuit suit;

    public String doAction() {
        Flash flash = FacesContext.getCurrentInstance().
                getExternalContext().getFlash();
        flash.put("firstName",firstName);
        flash.put("lastName",lastName);
        flash.put("suit", suit);
        return "/jsf-validation/french-suit-complete?redirect=true";
    }

    public String cancel() {
        return "/index.xhtml?redirect=true";
    }

    public FrenchSuit  suitEnumValue( String name ) {
        return FrenchSuit.valueOf(name);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public FrenchSuit getSuit() {
        return suit;
    }

    public void setSuit(FrenchSuit suit) {
        this.suit = suit;
    }

}
