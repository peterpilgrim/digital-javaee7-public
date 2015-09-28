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
import javax.servlet.http.HttpServletRequest;
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
        return Response.status(Response.Status.OK).entity("/products.jsp").build();
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
        if ( products.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("/error.jsp").build();
        }
        else {
            models.put("product", products.get(0));
            return Response.status(Response.Status.OK).entity("/delete-product.jsp").build();
        }
    }

    @POST
    @Controller
    @Path("delete/{id}")
    @Produces("text/html")
    public Response deleteProduct( @PathParam("id") int id, @FormParam("action") String action  )
    {
        System.out.printf("***** %s.deleteProduct( id=%d, action=%s ) productService=%s, models=%s\n", getClass().getSimpleName(), id, action, productService, models );
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
                return Response.status(Response.Status.OK).entity("/products.jsp").build();
            }
        }
        else {
            retrieveAll();
            return Response.status(Response.Status.OK).entity("/products.jsp").build();
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
                                 @Context HttpServletRequest request
    )
    {
        System.out.printf("***** %s.add() productService=%s, models=%s\n", getClass().getSimpleName(), productService, models );
        System.out.printf("***** name=%s, description=%s, price=%.4f\n", name, description, price);
        if ("Add".equalsIgnoreCase(action)) {
            final Product product = new Product(name, description, price);
            productService.saveProduct(product);
            models.put("product", product);
        }
        retrieveAll();
        return Response.status(Response.Status.OK).entity("/products.jsp").build();
    }
}
