package uk.co.xenonique.basic.mvc;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.Viewable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


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

    @EJB
    ProductService productService;

    @GET
    @Controller
    @Path("list")
    @Produces("text/html")
    public Viewable listProducts( )
    {
        System.out.printf("***** %s.listProducts() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        retrieveAll();
        return new Viewable("/products.jsp");
    }

    private void retrieveAll() {
        final List<Product> products = productService.findAll();
        System.out.printf("***** products=%s", products);
        models.put("products", products );
    }


    @GET
    @Controller
    @Path("view/{id}")
    @Produces("text/html")
    public Viewable retrieveProduct( @PathParam("id") int id  )
    {
        System.out.printf("***** %s.retrieveProduct( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        final List<Product> products = productService.findById(id);
        System.out.printf("***** products=%s", products);
        models.put("product", products.get(0) );
        return new Viewable("/product.jsp");
    }


    @POST
    @Controller
    @Path("edit/{id}")
    @Produces("text/html")
    public Response editProduct( @PathParam("id") int id,
                                 @FormParam("action") String action,
                                 @FormParam("name") String name,
                                 @FormParam("description") String description,
                                 @FormParam("price") double price,
                                 @Context HttpServletRequest request
    )
    {
        System.out.printf("***** %s.edit( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        System.out.printf("***** name=%s, description=%s, price=%.4f\n", name, description, price);
        if ("Save".equalsIgnoreCase(action)) {
            final List<Product> products = productService.findById(id);
            System.out.printf("***** products=%s", products);
            final Product product = products.get(0);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            productService.saveProduct(product);
            models.put("product", product);
        }
        retrieveAll();
//        return new Viewable("products.jsp");
//        return new Viewable("redirect:/products.jsp");
//        final Response response = Response.seeOther(URI.create("products.jsp")).build();
        // "location - the redirection URI. If a relative URI is supplied it will be converted into an absolute URI by resolving it relative to the base URI of the application (see UriInfo.getBaseUri())."
        final Response response = Response.status(Response.Status.OK).entity("/products.jsp").build();
        return response;
    }
}
