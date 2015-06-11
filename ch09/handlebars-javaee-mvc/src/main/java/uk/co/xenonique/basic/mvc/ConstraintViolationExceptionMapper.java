package uk.co.xenonique.basic.mvc;

import javax.annotation.Priority;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The type ConstraintViolationExceptionMapper
 *
 * @author Peter Pilgrim
 */
@Provider
@Priority(Priorities.USER)
public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response
                // Define your own status.
                .status(400)
                        // Process given Exception and set an entity
                        // i.e. Set an instance of Viewable to the response
                        // so that Jersey MVC support can handle it.
                .entity(new MyViewable("error.hbs", exception))
                .build();
    }

    private static class MyViewable {
        private final String view;
        private final Exception exception;

        public MyViewable(String view, Exception exception) {
            this.view = view;
            this.exception = exception;
        }

        @Override
        public String toString() {
            return view;
        }

        public Exception getException() {
            return exception;
        }
    }

}