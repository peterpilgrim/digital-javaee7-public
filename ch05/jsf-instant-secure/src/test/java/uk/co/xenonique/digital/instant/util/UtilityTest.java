package uk.co.xenonique.digital.instant.util;

import static org.junit.Assert.*;
import org.junit.*;

import java.math.BigDecimal;

public class UtilityTest {
    Utility utility = new Utility();

    /*
    Basic payment formula
    To calculate the mortgage payment, use the following formula:
    PMT = (PV x IR) / (1 - (1 + IR)-NP)
    Java implementation: PMT = (PV * IR) / (1 – Math.pow(1 + IR, -NP))
    Where:
    PMT = Monthly Payment
    PV = Principle Value (amount of loan)
    IR = Interest Rate, by month
    NP = Note Period, or mortgage term in months
    IR = APR / 100 / 12
    NP = Years * 12 (for example, 5 years means NP = 60)
    APR = Annual Percentage Rate (for example 6.25)
    Use the formula above when APR > 0.
    If APR = 0 (an interest-free loan), then PMT = PV / NP.

     */

	@Test
	public void calculateAmortisation() {
//        double pv = 10000.0;
//        double apr = 5.50;
//        double ir = apr / 100 / 12;
//        double np = 60;
//        double pmt = (pv * ir) / (1 - Math.pow(1+ir, -np));
        double pmt1 = calculateMonthlyPayment(10000.0, 5.50, 60 );
        System.out.printf("pmt (monthly payment) = %.2f\n", pmt1);
        double pmt2 = calculateMonthlyPayment(5000.0, 5.50, 60 );
        System.out.printf("pmt (monthly payment) = %.2f\n", pmt2);
	}

    /**
     * Calculates the monthly payment for a loan.
     *
     * @param pv the principal amount value
     * @param apr the Annual Percentage Rate
     * @param np the notice period, number of months of the loan agreement (Years * 12 (for example, 5 years means NP = 60))
     * @return the monthly payment
     */
    double calculateMonthlyPayment( double pv, double apr, int np ) {
        double ir = apr / 100 / 12;
        return (pv * ir) / (1 - Math.pow(1+ir, -np));
    }

    @Test
    public void calculate() {
        double pmt1 = utility.calculateMonthlyPayment(10000.0, 5.50, 60);
        System.out.printf("pmt1 (monthly payment) = %.2f\n", pmt1);
        assertEquals( 191.01, pmt1, 0.005);
    }

    @Test
    public void bounds() {
        assertEquals( 22.50, utility.getTaxRate( new BigDecimal("1000")).doubleValue(), 0.005);
        assertEquals( 9.79, utility.getTaxRate( new BigDecimal("4500")).doubleValue(), 0.005);
        assertEquals( 9.79, utility.getTaxRate( new BigDecimal("5000")).doubleValue(), 0.005);
        assertEquals( 7.49, utility.getTaxRate( new BigDecimal("6000")).doubleValue(), 0.005);
    }
}

