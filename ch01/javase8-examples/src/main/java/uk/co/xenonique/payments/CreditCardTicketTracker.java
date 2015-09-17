package uk.co.xenonique.payments;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.*;
import java.util.List;

/**
 * The type CreditCardTicketTracker
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class CreditCardTicketTracker {

    // Rely on CDI product factory to inject the correct type
    @Inject
    PaymentIssuer issuer;

    public void processTickets(List<Ticket> ticketBatch) {
        final LocalDate dt = LocalDate.now().plusDays(2);

        ticketBatch.stream()
                .filter(
                        t -> t.isAvailable() &&
                        t.getPaymentType() == PaymentType.CreditCard &&
                        dt.isAfter(DateUtils.asLocalDate(t.getConcertDate())))
                .map(t -> t.getAllocationId())
                .forEach(allocationId -> issuer.allocate(allocationId));
    }
}
