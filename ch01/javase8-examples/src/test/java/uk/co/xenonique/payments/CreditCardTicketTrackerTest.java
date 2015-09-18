package uk.co.xenonique.payments;

import static org.junit.Assert.*;

import org.junit.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;

/**
 * Verifies the operation of the CreditCardTicketTrackerTest
 *
 * @author Peter Pilgrim
 */
public class CreditCardTicketTrackerTest {
    @Test
    public void should_issue_ticket_with_credit_associate_allocation() {

        final Ticket ticket = new Ticket();
        ticket.setAvailable(true);
        ticket.setPaymentType(PaymentType.CreditCard);
        ticket.setConcertDate(DateUtils.asDate(LocalDateTime.of(2016, 4, 29, 20, 30)));

        final Allocation allocation = new Allocation("Exclusive VIP", "A", 1);
        ticket.setAllocation(allocation);

        final PaymentIssuerImpl paymentIssuer = new PaymentIssuerImpl();

        final CreditCardTicketTracker tracker = new CreditCardTicketTracker();
        tracker.setIssuer(paymentIssuer);
        tracker.processTickets(Arrays.asList(ticket));

        assertThat( paymentIssuer.getAllocations().isEmpty(), is(false));
        assertThat(paymentIssuer.getAllocations().size(), is(1));

        // We already know that there is one in the stream.
        final Allocation a1 = paymentIssuer.getAllocations().stream().findFirst().get();
        assertThat(a1, is(allocation));
    }
}
