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
/*
@Provider
@Priority(Priorities.USER)
*/
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