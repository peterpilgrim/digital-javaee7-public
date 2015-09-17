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
    public void should_issue_ticket() {

        final Ticket ticket = new Ticket();
        ticket.setAvailable(true);
        ticket.setPaymentType(PaymentType.CreditCard);
        ticket.setConcertDate(DateUtils.asDate(LocalDateTime.of(2016, 4, 29, 20, 30)));

        final Allocation allocation = new Allocation("Exclusive VIP", "A", 1);

        final PaymentIssuerImpl paymentIssuer = new PaymentIssuerImpl();

        final CreditCardTicketTracker tracker = new CreditCardTicketTracker();
        tracker.processTickets(Arrays.asList(ticket));

        assertThat( paymentIssuer.getAllocations().isEmpty(), is(false));

        assertThat(1 + 2, is(3));
//        assertThat(300, is(greaterThan(100)));
//        assertThat(new Object(), is(notNullValue()));
//        fail("implement this");
    }
}
