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
