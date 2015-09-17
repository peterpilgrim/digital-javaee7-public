package uk.co.xenonique.payments;

import java.util.Date;

/**
 * The type Ticket
 *
 * @author Peter Pilgrim
 */
public class Ticket {
    private boolean available;
    private PaymentType paymentType;
    private Date concertDate;
    private Allocation allocation;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Date getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(Date concertDate) {
        this.concertDate = concertDate;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }
}
