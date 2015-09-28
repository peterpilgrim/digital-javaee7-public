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

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.security.Principal;

/**
 * The type InfoSecurityComponent
 *
 * @author Peter Pilgrim
 */
@FacesComponent(
    value="information",
    namespace = "http:/www.xenonique.co.uk/jsf/instant/lending",
    tagName = "infoSec", createTag = true)
public class InfoSecurityComponent extends UINamingContainer {
    private String message;

    @Override
    public String getFamily() {
        return "instant.lending.custom.component";
    }

    @Override
    public Object saveState(FacesContext context) {
        final Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = message;
        return ((Object) (values));
    }

    @Override
    public void restoreState(FacesContext context, Object state) {
        final Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        message = (String) values[1];
    }

    public void encodeBegin(FacesContext context)
            throws IOException {
        final ResponseWriter writer =
                context.getResponseWriter();
        writer.startElement("div", this);
        writer.writeAttribute("role", "alert", null );
        final Principal principal = FacesContext.getCurrentInstance()
            .getExternalContext().getUserPrincipal();
        String name;
        if ( principal !=null ) {
            writer.writeAttribute("class","alert  alert-success",null);
            name = principal.getName();
        }
        else {
            writer.writeAttribute("class","alert  alert-danger",null);
            name = "unknown";
        }
        writer.write(
            String.format("[USER: <strong>%s</strong>] - %s",
            name, message));
    }

    public void encodeEnd(FacesContext context)
            throws IOException {
        final ResponseWriter writer =
                context.getResponseWriter();
        writer.endElement("div");
        writer.flush();
    }

    // Getters and setter omitted
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
