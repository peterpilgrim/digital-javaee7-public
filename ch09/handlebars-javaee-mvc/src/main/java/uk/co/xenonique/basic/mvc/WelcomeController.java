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

    private void defineCommonModelProperties(HttpServletRequest request, HttpServletResponse response ) {
        models.put("pageTitle", "Handlebars.java Java EE 8 MVC" );
        models.put("title", "Welcome");
        models.put("webContextPath", request.getContextPath() );
        models.put("request", request);
        models.put("response", response);
        models.put("page", request.getRequestURI() );
    }

    @GET
    @Controller
    @Produces("text/html")
    public Viewable welcome( @Context HttpServletRequest request, @Context HttpServletResponse response)
    {
        System.out.printf("***** %s.welcome() models=%s\n", getClass().getSimpleName(), models );
        defineCommonModelProperties(request, response);
        return new Viewable("/welcome.hbs");
    }

}
