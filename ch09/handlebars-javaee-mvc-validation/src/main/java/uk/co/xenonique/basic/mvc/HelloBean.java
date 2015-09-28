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
import javax.mvc.Viewable;
import javax.ws.rs.*;

/**
 * The type HelloBean
 *
 * @author Peter Pilgrim
 */
@Path("/hello")
@Stateless
public class HelloBean {

    @Inject
    User user;

    @GET
    @Controller
    @Path("view/{name}")
    @Produces("text/html")
    public Viewable fire(  @PathParam("name") String name )
    {
        System.out.printf("%s.fire( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.hbs");
    }

    @POST
    @Controller
    @Path("queryform")
    @Produces("text/html")
    public Viewable hello(  @FormParam("name") String name )
    {
        System.out.printf("%s.hello( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.hbs");
    }
}
