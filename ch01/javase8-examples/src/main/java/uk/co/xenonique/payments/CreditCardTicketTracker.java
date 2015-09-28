/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

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

    public PaymentIssuer getIssuer() {
        return issuer;
    }

    public void setIssuer(PaymentIssuer issuer) {
        this.issuer = issuer;
    }

    public void processTickets(List<Ticket> ticketBatch) {
        final LocalDate dt = LocalDate.now().plusDays(2);

        ticketBatch.stream()
            .filter(
                t -> t.isAvailable() &&
                t.getPaymentType() == PaymentType.CreditCard &&
                dt.isBefore(DateUtils.asLocalDate(t.getConcertDate())))
            .map(t -> t.getAllocation().allocateToTicket(t))
            .forEach(allocation -> issuer.allocate(allocation));
    }
}
