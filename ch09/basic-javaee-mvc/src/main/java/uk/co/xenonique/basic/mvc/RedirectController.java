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

    @GET
    @Path("response1")
    public Response getResponse1() {
        return Response.seeOther(URI.create("redirect/here")).build();
    }

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
