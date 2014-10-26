package uk.co.xenonique.digital.instant.util;

import java.math.BigDecimal;

/**
 * The type LoanRateBounds
 *
 * @author Peter Pilgrim
 */
public class LoanRateBounds {
    private final BigDecimal lower;
    private final BigDecimal upper;
    private final BigDecimal rate;

    public LoanRateBounds(String lower, String upper, String rate) {
        this(new BigDecimal(lower), new BigDecimal(upper),
                new BigDecimal(rate));
    }

    public LoanRateBounds(final BigDecimal lower, final BigDecimal upper,
                          final BigDecimal rate) {
        this.lower = lower;
        this.upper = upper;
        this.rate = rate;
    }

    public BigDecimal getLower() {
        return lower;
    }

    public BigDecimal getUpper() {
        return upper;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoanRateBounds that = (LoanRateBounds) o;

        if (lower != null ? !lower.equals(that.lower) : that.lower != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (upper != null ? !upper.equals(that.upper) : that.upper != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lower != null ? lower.hashCode() : 0;
        result = 31 * result + (upper != null ? upper.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoanRateBounds{" +
                "lower=" + lower +
                ", upper=" + upper +
                ", rate=" + rate +
                '}';
    }
}