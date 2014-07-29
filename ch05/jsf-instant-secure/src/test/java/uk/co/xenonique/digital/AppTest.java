package uk.co.xenonique.digital;

import static org.junit.Assert.*;
import org.junit.*;

public class  AppTest {

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
}

