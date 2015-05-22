package acme.demo;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * The type SampleEndpoint
 *
 * @author Peter Pilgrim
 */
@Stateless
@Path("/sample")
public class SampleEndpoint {

    @GET
    @Path("/hello")
    public String hello() {
        return "You reached the sample endpoint";
    }
}
