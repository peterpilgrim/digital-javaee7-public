package uk.co.xenonique.basic.mvc;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Date;

/**
 * The type SampleEndpoint
 *
 * @author Peter Pilgrim
 */
@Stateless
@Path("/sample")
public class SampleRESTServerEndpoint {

    @GET
    @Path("/hello")
    public String hello() {
        return String.format("You reached the sample REST endpoint [date: %s, class: %s]", new Date(), this.getClass().getName()) ;
    }
}
