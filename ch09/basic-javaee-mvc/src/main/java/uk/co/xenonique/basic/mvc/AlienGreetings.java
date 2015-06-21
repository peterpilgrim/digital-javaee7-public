package uk.co.xenonique.basic.mvc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.View;
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

    @GET
    @Controller
    @Path("simple1")
    @Produces("text/html")
    public String simple1(  @QueryParam("name") String name )
    {
        System.out.printf("%s.hello( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return "/hello.jsp";
    }

    @GET
    @Controller
    @Path("simple2")
    @Produces("text/html")
    public Viewable simple2(  @QueryParam("name") String name )
    {
        System.out.printf("%s.hello( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.jsp");
    }

    @GET
    @Controller
    @Path("simple3")
    @Produces("text/html")
    @View("/hello.jsp")
    public void simple3(  @QueryParam("name") String name )
    {
        System.out.printf("%s.hello( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
    }

    @GET
    @Controller
    @Path("view/{name}")
    @Produces("text/html")
    public Viewable helloWebPath( @PathParam("name") String name )
    {
        System.out.printf("%s.helloWebPath( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.jsp");
    }

    @POST
    @Controller
    @Path("webform")
    @Produces("text/html")
    public Viewable helloWebForm( @FormParam("name") String name )
    {
        System.out.printf("%s.helloWebForm( name=`%s' )\n", getClass().getSimpleName(), name );
        user.setName(name);
        return new Viewable("/hello.jsp");
    }
}
