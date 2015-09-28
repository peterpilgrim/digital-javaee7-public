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
import javax.mvc.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * PersonController test.
 *
 * @author Peter Pilgrim
 */
@Path("person")
@Controller
@Stateless
public class PersonController {

    @Inject
    private Models models;

    @GET
    @Produces("text/html;Charset=UTF-8")
    @View("person.hbs")
    public void person( @Context HttpServletRequest request, @Context HttpServletResponse response) {
        final Person person = new Person();
        person.setFirstName("Richard");
        person.setLastName("Davies");
        person.setAge(36);
        models.put("person", person);
        models.put("title", "Handlebars Java and Java EE 8 MVC Framework");
        models.put("webContextPath", request.getContextPath() );
        models.put("request", request);
        models.put("response", response);
        models.put("page", request.getRequestURI() );
    }

}
