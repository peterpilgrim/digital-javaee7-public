package uk.co.xenonique.basic.mvc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.Viewable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * The type ProductController
 *
 * @author Peter Pilgrim
 */
@Path("/products")
@Stateless
public class ProductController {

    @Inject
    Models  models;

    @GET
    @Controller
    @Path("view/{name}")
    @Produces("text/html")
    public Viewable workProduct(  @PathParam("name") String name )
    {
        System.out.printf("%s.workProduct( name=`%s' )  models=%s\n", getClass().getSimpleName(), name, models );

        final Product product = new Product();
        product.setName(name);
        product.setPrice(78.99);
        models.put("product", product );
        return new Viewable("/product.jsp");
    }


}
