package uk.co.xenonique.nationalforce;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.function.Function;

/**
 * The type DebugConstraintViolationHelpers
 *
 * @author Peter Pilgrim
 */
public class DebugConstraintViolationHelpers {

    public static void execute( Runnable lambda ) {

        try {
            lambda.run();
        }
        catch (ConstraintViolationException cve) {
            for ( ConstraintViolation violation: cve.getConstraintViolations() ) {
                System.err.printf("****** CONSTRAINT VIOLATION message: %s, propertyPath=%s, invalidValue=%s, leafBean=%s\n",
                    violation.getMessage(), violation.getPropertyPath(), violation.getInvalidValue(),
                    violation.getLeafBean());
            }
            throw cve;  // RETHROW
        }
    }
}
