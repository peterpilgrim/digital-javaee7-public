package uk.co.xenonique.payments;

/**
 * The type PaymentIssuer
 *
 * @author Peter Pilgrim
 */
public interface PaymentIssuer {
    void allocate(int allocationId);
}
