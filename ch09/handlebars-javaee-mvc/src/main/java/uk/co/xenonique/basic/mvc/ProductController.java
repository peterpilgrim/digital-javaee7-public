package uk.co.xenonique.basic.mvc;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.Viewable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
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

    private void defineCommonModelProperties(HttpServletRequest request, HttpServletResponse response, String title ) {
        models.put("pageTitle", "Handlebars.java Java EE 8 MVC" );
        models.put("title", title);
        models.put("webContextPath", request.getContextPath() );
        models.put("request", request);
        models.put("response", response);
        models.put("page", request.getRequestURI() );
    }

    private void retrieveAll() {
        final List<Product> products = productService.findAll();
        System.out.printf("***** products=%s", products);
        models.put("products", products );
        models.put("title", "Products");
    }

    @GET
    @Controller
    @Path("list")
    @Produces("text/html")
    public Viewable listProducts( @Context HttpServletRequest request, @Context HttpServletResponse response)
    {
        System.out.printf("***** %s.listProducts() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        retrieveAll();
        defineCommonModelProperties(request, response, "Products");
        return new Viewable("/products.hbs");
    }

    @GET
    @Controller
    @Path("view/{id}")
    @Produces("text/html")
    public Viewable retrieveProduct( @PathParam("id") int id, @Context HttpServletRequest request, @Context HttpServletResponse response )
    {
        System.out.printf("***** %s.retrieveProduct( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        final List<Product> products = productService.findById(id);
        System.out.printf("***** products=%s", products);
        models.put("product", products.get(0) );
        defineCommonModelProperties(request, response, "Product");
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
                                 @Context HttpServletRequest request, @Context HttpServletResponse response    )
    {
        System.out.printf("***** %s.edit( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        System.out.printf("***** name=%s, description=%s, price=%.4f\n", name, description, price);
        defineCommonModelProperties(request,response,"Edit Product");
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
        final Response res = Response.status(Response.Status.OK).entity("/edit-products.hbs").build();
        return res;
    }


    @GET
    @Controller
    @Path("preview-delete/{id}")
    @Produces("text/html")
    public Response previewDeleteProduct( @PathParam("id") int id, @Context HttpServletRequest request, @Context HttpServletResponse response)
    {
        System.out.printf("***** %s.previewDeleteProduct( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        final List<Product> products = productService.findById(id);
        System.out.printf("***** products=%s", products);
        defineCommonModelProperties(request, response, "Delete Product");
        if ( products.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("/error.jsp").build();
        }
        else {
            models.put("product", products.get(0));
            final Response res = Response.status(Response.Status.OK).entity("/delete-product.jsp").build();
            return res;
        }
    }

    @POST
    @Controller
    @Path("delete/{id}")
    @Produces("text/html")
    public Response deleteProduct( @PathParam("id") int id, @FormParam("action") String action,
                                   @Context HttpServletRequest request, @Context HttpServletResponse response  )
    {
        System.out.printf("***** %s.deleteProduct( id=%d, action=%s ) productService=%s, models=%s\n", getClass().getSimpleName(), id, action, productService, models );
        defineCommonModelProperties(request, response, "Remove");
        if ( "Remove".equalsIgnoreCase(action)) {
            final List<Product> products = productService.findById(id);
            System.out.printf("***** products=%s", products);
            if ( products.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("/error.jsp").build();
            }
            else {
                productService.removeProduct(products.get(0));
                models.put("product", products.get(0) );
                retrieveAll();
                final Response res = Response.status(Response.Status.OK).entity("/products.jsp").build();
                return res;
            }
        }
        else {
            retrieveAll();
            final Response res = Response.status(Response.Status.OK).entity("/products.jsp").build();
            return res;
        }
    }

    @POST
    @Controller
    @Path("add")
    @Produces("text/html")
    public Response addProduct(@FormParam("action") String action,
                               @FormParam("name") String name,
                               @FormParam("description") String description,
                               @FormParam("price") double price,
                               @Context HttpServletRequest request, @Context HttpServletResponse response    )
    {
        System.out.printf("***** %s.add() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        System.out.printf("***** name=%s, description=%s, price=%.4f\n", name, description, price);
        defineCommonModelProperties(request, response, "Add Product");
        if ("Add".equalsIgnoreCase(action)) {
            final Product product = new Product(name, description, price);
            productService.saveProduct(product);
            models.put("product", product);
        }
        retrieveAll();
        final Response res = Response.status(Response.Status.OK).entity("/products.jsp").build();
        return res;
    }
}
