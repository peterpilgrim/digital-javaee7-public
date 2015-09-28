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
        catch (Throwable t) {
            t.printStackTrace(System.err);
            throw t;
        }
    }
}
