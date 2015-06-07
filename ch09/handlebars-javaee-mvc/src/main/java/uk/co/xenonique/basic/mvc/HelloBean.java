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
