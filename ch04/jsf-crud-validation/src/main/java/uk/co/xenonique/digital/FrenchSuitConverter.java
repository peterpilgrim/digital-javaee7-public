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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import static uk.co.xenonique.digital.FrenchSuit.*;

/**
 * The type FrenchSuitConverter
 *
 * @author Peter Pilgrim
 */
@FacesConverter("frenchSuitConverter")
public class FrenchSuitConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String text = value.trim();
        if ( text.length() == 0 ) {
            text = ((UIInput)component).getSubmittedValue().toString();
        }
        text = text.toUpperCase();
        switch (text) {
            case "HEARTS": return HEARTS;
            case "DIAMONDS": return DIAMONDS;
            case "CLUBS": return CLUBS;
            case "SPADES": return SPADES;
            default:
                throw new ConverterException(
                    new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "{application.frenchSuit.convertToObject}",
                        "Unable to convert object to string"));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if ( value instanceof String ) {
            return value.toString();
        }
        else if ( !(value instanceof FrenchSuit)) {
            throw new ConverterException(
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "{application.frenchSuit.convertToString}",
                    "Unable to convert object to string"));
        }
        switch ((FrenchSuit)value) {
            case HEARTS: return "Hearts";
            case DIAMONDS: return "Diamonds";
            case CLUBS: return "Clubs";
            case SPADES: return "Spades";
        }
        throw new IllegalStateException(
            "PC should never reach here!");
    }
}
