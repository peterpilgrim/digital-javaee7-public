package uk.co.xenonique.payments;

import java.util.HashSet;
import java.util.Set;

/**
 * The type PaymentIssuerImpl
 *
 * @author Peter Pilgrim
 */
public class PaymentIssuerImpl implements PaymentIssuer {

    private Set<Allocation> allocations = new HashSet<>();

    @Override
    public void allocate(Allocation allocation) {
        allocations.add(allocation);
    }


    public Set<Allocation> getAllocations() {
        return allocations;
    }

}
