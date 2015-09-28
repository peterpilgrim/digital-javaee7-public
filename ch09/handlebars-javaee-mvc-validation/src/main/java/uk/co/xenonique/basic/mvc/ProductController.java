/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package uk.co.xenonique.basic.mvc;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.Viewable;
import javax.mvc.validation.ValidationResult;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
/**
 * The type ProductController
 *
 * @author Peter Pilgrim
 */
@Path("/products")
@Stateless
public class ProductController {
    @Inject Models models;

    @Inject ValidationResult vr;

    @Inject FormErrorMessage formError;

    @EJB ProductService productService;

    private void defineCommonModelProperties(String title) {
        models.put("pageTitle", "Handlebars.java Java EE 8 MVC Validation" );
        models.put("title", title);
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
    public Viewable listProducts()
    {
        System.out.printf("***** %s.listProducts() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        retrieveAll();
        defineCommonModelProperties( "Products");
        return new Viewable("/products.hbs");
    }

    @GET
    @Controller
    @Path("view/{id}")
    @Produces("text/html")
    public Viewable retrieveProduct( @PathParam("id") int id )
    {
        System.out.printf("***** %s.retrieveProduct( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        final List<Product> products = productService.findById(id);
        System.out.printf("***** products=%s", products);
        models.put("product", products.get(0) );
        defineCommonModelProperties("Product");
        return new Viewable("/edit-product.hbs");
    }

    @GET
    @Controller
    @Path("preview-create")
    @Produces("text/html")
    public Response previewCreateProduct()
    {
        System.out.printf("***** %s.previewCreateProduct() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        defineCommonModelProperties("Create Product");
        models.put("product", new Product() );
        return Response.status(OK).entity("/create-product.hbs").build();
    }

    @POST
    @Controller
    @Path("add")
    @Produces("text/html")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public Response addProduct(@FormParam("action") String action,
                               @Valid @BeanParam Product product)
    {
        System.out.printf("***** %s.add() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        System.out.printf("***** action=%s, product=%s\n", action, product );
        defineCommonModelProperties("Add Product");
        if ("Add".equalsIgnoreCase(action)) {
            final Set<ConstraintViolation<?>> set = vr.getAllViolations();
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
        return Response.status(OK).entity("/products.hbs").build();
    }

    @POST
    @Controller
    @Path("edit/{id}")
    @Produces("text/html")
    /*  @View("error.hbs") */
    @ValidateOnExecution(type = ExecutableType.NONE)
    public Response editProduct( @PathParam("id") int id,
                                 @FormParam("action") String action,
                                 @Valid @BeanParam Product incomingProduct)
    {
        System.out.printf("***** %s.edit( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        System.out.printf("***** action=%s, incomingProduct=%s\n", action, incomingProduct );
        defineCommonModelProperties("Edit Product");
        if ("Save".equalsIgnoreCase(action)) {
            final Set<ConstraintViolation<?>> set = vr.getAllViolations();
            if (!set.isEmpty()) {
                final ConstraintViolation<?> cv = set.iterator().next();
                final String property = cv.getPropertyPath().toString();
                formError.setProperty(property.substring(property.lastIndexOf('.') + 1));
                formError.setValue(cv.getInvalidValue());
                formError.setMessage(cv.getMessage());
                models.put("formError",formError);
                return Response.status(BAD_REQUEST).entity("error.hbs").build();
            }

            final List<Product> products = productService.findById(id);
            final Product product = products.get(0);
            product.setName(incomingProduct.getName());
            product.setDescription(incomingProduct.getDescription());
            product.setPrice(incomingProduct.getPrice());
            productService.saveProduct(product);
            models.put("product", product);
        }
        retrieveAll();
        return Response.status(OK).entity("/products.hbs").build();
    }

    @GET
    @Controller
    @Path("preview-delete/{id}")
    @Produces("text/html")
    public Response previewDeleteProduct( @PathParam("id") int id)
    {
        System.out.printf("***** %s.previewDeleteProduct( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        final List<Product> products = productService.findById(id);
        System.out.printf("***** products=%s", products);
        defineCommonModelProperties("Delete Product");
        if ( products.isEmpty()) {
            return Response.status(BAD_REQUEST).entity("/error.jsp").build();
        }
        else {
            models.put("product", products.get(0));
            return Response.status(OK).entity("/delete-product.hbs").build();
        }
    }

    @POST
    @Controller
    @Path("delete/{id}")
    @Produces("text/html")
    public Response deleteProduct( @PathParam("id") int id, @FormParam("action") String action)
    {
        System.out.printf("***** %s.deleteProduct( id=%d, action=%s ) productService=%s, models=%s\n", getClass().getSimpleName(), id, action, productService, models );
        defineCommonModelProperties( "Remove");
        if ( "Remove".equalsIgnoreCase(action)) {
            final List<Product> products = productService.findById(id);
            System.out.printf("***** products=%s", products);
            if ( products.isEmpty()) {
                formError.setMessage(String.format("There is no product with the following id: [%d]", id ));
                models.put("formError", formError);
                return Response.status(BAD_REQUEST).entity("/error.jsp").build();
            }
            else {
                productService.removeProduct(products.get(0));
                models.put("product", products.get(0) );
                retrieveAll();
                return Response.status(OK).entity("/products.hbs").build();
            }
        }
        else {
            retrieveAll();
            return Response.status(OK).entity("/products.hbs").build();
        }
    }

/*

    @POST
    @Controller
    @Path("alt-edit/{id}")
    @Produces("text/html")
    public Response alternativeEditProduct( @PathParam("id") int id,
         @FormParam("action") String action,
         @NotNull @NotEmpty @FormParam("name") String name,
         @NotNull @NotEmpty @FormParam("description") String description,
         @NotNull @DecimalMin("0.0") @FormParam("price") BigDecimal price,
         @Context HttpServletRequest request, @Context HttpServletResponse response )
    {
        // This does NOT work with MVC 1.0.0-m1
        System.out.printf("***** %s.edit( id=%d ) productService=%s, models=%s\n", getClass().getSimpleName(), id, productService, models );
        System.out.printf("***** name=%s, description=%s, price=%.4f\n", name, description, price);
        defineCommonModelProperties("Edit Product");
//        System.out.printf("**** vr=%s, vr.failed = %s\n", vr, vr.isFailed());
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
        return Response.status(OK).entity("/products.hbs").build();
    }
    */
}
