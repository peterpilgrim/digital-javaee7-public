package uk.co.xenonique.basic.mvc;

import org.hibernate.validator.constraints.NotEmpty;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.Viewable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

 import static javax.ws.rs.core.Response.Status.*;
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

    @Inject
    ValidatorFactory validatorFactory;

    @Inject
    FormErrorMessage formError;

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
        return new Viewable("/edit-product.hbs");
    }

    @GET
    @Controller
    @Path("preview-create")
    @Produces("text/html")
    public Response previewCreateProduct(@Context HttpServletRequest request, @Context HttpServletResponse response)
    {
        System.out.printf("***** %s.previewCreateProduct() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        defineCommonModelProperties(request, response, "Create Product");
        models.put("product", new Product() );
        return Response.status(Response.Status.OK).entity("/create-product.hbs").build();
    }

    @POST
    @Controller
    @Path("add")
    @Produces("text/html")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public Response addProduct(@FormParam("action") String action,
                               @FormParam("name") String name,
                               @FormParam("description") String description,
                               @FormParam("price") BigDecimal price,
                               @Context HttpServletRequest request, @Context HttpServletResponse response    )
    {
        System.out.printf("***** %s.add() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        System.out.printf("***** name=%s, description=%s, price=%.4f\n", name, description, price);
        defineCommonModelProperties(request, response, "Add Product");
        if ("Add".equalsIgnoreCase(action)) {
            final Product product = new Product(name, description, price);
//            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//            final Set<ConstraintViolation<Product>> set = factory.getValidator().validate(product);
            final Set<ConstraintViolation<Product>> set = validatorFactory.getValidator().validate(product);
            if (!set.isEmpty()) {
                final ConstraintViolation<?> cv = set.iterator().next();
                final String property = cv.getPropertyPath().toString();
                formError.setProperty(property.substring(property.lastIndexOf('.') + 1));
                formError.setValue(cv.getInvalidValue());
                formError.setMessage(cv.getMessage());
                models.put("formError",formError);
                return Response.status(BAD_REQUEST).entity("error.hbs").build();
            }

            productService.saveProduct(product);
            models.put("product", product);
        }
        retrieveAll();
        return Response.status(Response.Status.OK).entity("/products.hbs").build();
    }

    @POST
    @Controller
    @Path("edit/{id}")
    @Produces("text/html")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public Response editProduct( @PathParam("id") int id,
                                 @FormParam("action") String action,
                                 @NotNull @NotEmpty @FormParam("name") String name,
                                 @NotNull @NotEmpty @FormParam("description") String description,
                                 @DecimalMin("0.0") @FormParam("price") BigDecimal price,
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
            final Set<ConstraintViolation<Product>> set = validatorFactory.getValidator().validate(product);
            if (!set.isEmpty()) {
                final ConstraintViolation<?> cv = set.iterator().next();
                final String property = cv.getPropertyPath().toString();
                formError.setProperty(property.substring(property.lastIndexOf('.') + 1));
                formError.setValue(cv.getInvalidValue());
                formError.setMessage(cv.getMessage());
                models.put("formError",formError);
                return Response.status(BAD_REQUEST).entity("error.hbs").build();
            }
            productService.saveProduct(product);
            models.put("product", product);
        }
        retrieveAll();
        return Response.status(Response.Status.OK).entity("/products.hbs").build();
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
            return Response.status(Response.Status.OK).entity("/delete-product.hbs").build();
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
                formError.setMessage(String.format("There is no product with the following id: [%d]", id ));
                models.put("formError", formError);
                return Response.status(Response.Status.BAD_REQUEST).entity("/error.jsp").build();
            }
            else {
                productService.removeProduct(products.get(0));
                models.put("product", products.get(0) );
                retrieveAll();
                return Response.status(Response.Status.OK).entity("/products.hbs").build();
            }
        }
        else {
            retrieveAll();
            return Response.status(Response.Status.OK).entity("/products.hbs").build();
        }
    }

}
