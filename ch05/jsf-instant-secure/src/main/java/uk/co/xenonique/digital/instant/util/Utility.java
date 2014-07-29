package uk.co.xenonique.digital.instant.util;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

/**
 * The type Utility
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class Utility implements Serializable {
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
