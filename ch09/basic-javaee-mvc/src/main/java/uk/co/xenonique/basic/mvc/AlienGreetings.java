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
public class AlienGreetings {

    @Inject
    User user;

    @POST
    @Controller
    @Path("simple1")
    @Produces("text/html")
    public String simple1(  @QueryParam("name") String name )
    {
        System.out.printf("%s.hello( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return "/hello.jsp";
    }

    @POST
    @Controller
    @Path("simple")
    @Produces("text/html")
    public Viewable simple2(  @QueryParam("name") String name )
    {
        System.out.printf("%s.hello( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.jsp");
    }

    @GET
    @Controller
    @Path("view/{name}")
    @Produces("text/html")
    public Viewable fire(  @PathParam("name") String name )
    {
        System.out.printf("%s.fire( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.jsp");
    }

    @POST
    @Controller
    @Path("queryform")
    @Produces("text/html")
    public Viewable hello(  @FormParam("name") String name )
    {
        System.out.printf("%s.hello( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.jsp");
    }
}
