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

package uk.co.xenonique.basic.mvc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.Viewable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * The type WelcomeController
 *
 * @author Peter Pilgrim
 */
@Path("/welcome")
@Stateless
public class WelcomeController {
    @Inject
    Models models;

    @GET
    @Controller
    @Produces("text/html")
    public Viewable welcome()
    {
        System.out.printf("***** %s.welcome() models=%s\n", getClass().getSimpleName(), models );
        models.put("pageTitle", "Handlebars.java Java EE 8 MVC" );
        models.put("title", "Welcome");
        return new Viewable("/welcome.hbs");
    }
}
