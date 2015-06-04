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
 * @author Rahman Usta
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
        Person person = new Person();
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
