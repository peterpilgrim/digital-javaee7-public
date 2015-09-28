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

import javax.annotation.Priority;
import javax.mvc.Models;
import javax.mvc.Viewable;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.*;

/**
 * The type ConstraintViolationExceptionMapper
 *
 * @author Peter Pilgrim
 */
@Provider
@Priority(Priorities.USER)
public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Context HttpServletRequest request;

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        final Models models = new com.oracle.ozark.core.Models();
        final ConstraintViolation<?> cv = exception.getConstraintViolations().iterator().next();
        final String property = cv.getPropertyPath().toString();
        final FormErrorMessage formError = new FormErrorMessage();
        formError.setProperty(property.substring(property.lastIndexOf('.') + 1));
        formError.setValue(cv.getInvalidValue());
        formError.setMessage(cv.getMessage());
        models.put("formError",formError);
        final Viewable viewable = new Viewable("error.hbs", models);
        return Response.status(BAD_REQUEST).entity(viewable).build();
    }
}