package uk.co.xenonique.basic.mvc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * The type PhobosGreetings
 *
 * @author Peter Pilgrim
 */
@Path("/hello2")
@Stateless
public class PhobosGreetings {
    @Inject
    Models models;

    @GET
    @Controller
    @Path("simple2")
    @Produces("text/html")
    public Viewable simple1(  @QueryParam("name") String name )
    {
        models.put("users", new User(name));
        return new Viewable("/hello2.jsp");
    }
}

