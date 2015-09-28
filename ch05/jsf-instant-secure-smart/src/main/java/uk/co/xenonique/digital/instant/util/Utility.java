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

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * The type Utility
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class Utility implements Serializable {

    protected List<LoanRateBounds> bounds = Arrays.asList(
            new LoanRateBounds("0.0",       "4500.0",   "22.50"),
            new LoanRateBounds("4500.0",    "6000.0",   "9.79"),
            new LoanRateBounds("6000.0",    "9000.0",   "7.49"),
            new LoanRateBounds("9000.0",    "11500.0",  "4.49"),
            new LoanRateBounds("11500.0",   "15000.0",  "4.29"),
            new LoanRateBounds("15000.0",   "20000.0",  "5.79"),
            new LoanRateBounds("20000.0",   "25000.0",  "6.29"),
            new LoanRateBounds("30000.0",   "50000.0",  "6.99")
            );

    public BigDecimal getTaxRate( BigDecimal amount ) {
        for ( LoanRateBounds bound : bounds ) {
            if ( bound.getLower().compareTo(amount) <= 0 &&
                    bound.getUpper().compareTo(amount) > 0 ) {
                return  bound.getRate();
            }

        }
        throw new IllegalArgumentException("no tax rate found in bounds");
    }
    /**
     * Calculates the monthly payment for a loan using the Basic payment formula.
     *
     * <p>
     * To calculate the mortgage payment, use the following formula:
     * <code>PMT = (PV x IR) / (1 - (1 + IR)-NP) </code>
     *
     *</p>
     *
     * <p>
     * Java implementation: <br><code>PMT = (PV * IR) / (1 – Math.pow(1 + IR, -NP))</code>
     * </p>
     *
     * <p>Where:
     *
     * <br>PMT = Monthly Payment
     * <br>PV = Principle Value (amount of loan)
     * <br>IR = Interest Rate, by month
     * <br>NP = Note Period, or mortgage term in months
     * <br>IR = APR / 100 / 12
     * <br>NP = Years * 12 (for example, 5 years means NP = 60)
     * <br>APR = Annual Percentage Rate (for example 6.25)
     * <br>Use the formula above when APR > 0.
     * If APR = 0 (an interest-free loan), then PMT = PV / NP.
     *
     * </p>
     *
     *
     * @param pv the principal amount value
     * @param apr the Annual Percentage Rate
     * @param np the notice period, number of months of the loan agreement (Years * 12 (for example, 5 years means NP = 60))
     * @return the monthly payment
     */
    public double calculateMonthlyPayment( final double pv, final double apr, final int np ) {
        double ir = apr / 100 / 12;
        return (pv * ir) / (1 - Math.pow(1+ir, -np));
    }
}
