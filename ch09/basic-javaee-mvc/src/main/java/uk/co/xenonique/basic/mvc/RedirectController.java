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
import javax.mvc.Controller;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

/**
 * The type RedirectController
 *
 * @author Peter Pilgrim
 */
@Path("redirect")
@Stateless
@Controller
public class RedirectController {

    @GET
    @Path("string")
    public String getString() {
        return "redirect:rredirect/here";
    }


    // This does work with 1.0.0-m1
    // http://localhost:8080/basic-javaee-mvc-1.0-SNAPSHOT/rest/redirect/response1 -> 500 Internal Server Error
    @GET
    @Path("response1")
    public Response getResponse1() {
        return Response.seeOther(URI.create("redirect/here")).build();
    }

    // Ditto - this does work with 1.0.0-m1
    // http://localhost:8080/basic-javaee-mvc-1.0-SNAPSHOT/rest/redirect/response2 -> 500 Internal Server Error
    @GET
    @Path("response2")
    public Response getResponse2() {
        return Response.status(Response.Status.FOUND)
                .header("Location", "redirect/here")
                .build();
    }

    @GET
    @Path("here")
    @Produces("text/html")
    public String getSub() {
        return "redirected.jsp";
    }
}
