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

package uk.co.xenonique.digital.instant.util;

import static org.junit.Assert.*;
import org.junit.*;

import java.math.BigDecimal;

public class UtilityTest {
    public static final double EPSILON = 0.005;
    Utility utility = new Utility();

    @Test
    public void calculateMonthlyPayment() {
        double pmt1 = utility.calculateMonthlyPayment(10000.0, 5.50, 60);
        System.out.printf("pmt1 (monthly payment) = %.2f\n", pmt1);
        assertEquals( 191.01, pmt1, EPSILON);
    }

    @Test
    public void validateLoanRateBounds() {
        assertEquals( 22.50, utility.getTaxRate( new BigDecimal("1000")).doubleValue(), EPSILON);
        assertEquals( 9.79, utility.getTaxRate( new BigDecimal("4500")).doubleValue(), EPSILON);
        assertEquals( 9.79, utility.getTaxRate( new BigDecimal("5000")).doubleValue(), EPSILON);
        assertEquals( 7.49, utility.getTaxRate( new BigDecimal("6000")).doubleValue(), EPSILON);

        BigDecimal theta = new BigDecimal(EPSILON);
        for ( LoanRateBounds bound : utility.bounds) {
            BigDecimal amount1 = new BigDecimal(bound.getLower().doubleValue());
            assertEquals(bound.getRate().doubleValue(), utility.getTaxRate(amount1).doubleValue(), EPSILON);

            BigDecimal amount2 = bound.getUpper().subtract(theta);
            assertEquals( bound.getRate().doubleValue(), utility.getTaxRate(amount2).doubleValue(), EPSILON);
        }
    }
}

