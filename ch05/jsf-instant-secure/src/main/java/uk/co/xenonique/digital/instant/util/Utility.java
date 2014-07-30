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

    private List<LoanRateBounds> bounds = Arrays.asList(
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
     * Calculates the monthly payment for a loan.
     *
     * @param pv the principal amount value
     * @param apr the Annual Percentage Rate
     * @param np the notice period, number of months of the loan agreement (Years * 12 (for example, 5 years means NP = 60))
     * @return the monthly payment
     */
    public double calculateMonthlyPayment( double pv, double apr, int np ) {
        double ir = apr / 100 / 12;
        return (pv * ir) / (1 - Math.pow(1+ir, -np));
    }
}
