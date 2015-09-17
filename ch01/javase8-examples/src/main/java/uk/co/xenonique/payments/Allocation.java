package uk.co.xenonique.payments;

/**
 * The type Allocation
 *
 * @author Peter Pilgrim
 */
public class Allocation {
    private String seatingBank;
    private String row;
    private int seatNumber;
    private Ticket ticket;

    public Allocation() {
        super();
    }

    public Allocation(String seatingBank, String row, int seatNumber) {
        this.seatingBank = seatingBank;
        this.row = row;
        this.seatNumber = seatNumber;
    }

    public String getSeatingBank() {
        return seatingBank;
    }

    public void setSeatingBank(String seatingBank) {
        this.seatingBank = seatingBank;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Allocation allocateToTicket( Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Allocation{");
        sb.append("seatingBank='").append(seatingBank).append('\'');
        sb.append(", row='").append(row).append('\'');
        sb.append(", seatNumber=").append(seatNumber);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Allocation)) return false;

        Allocation that = (Allocation) o;

        if (seatNumber != that.seatNumber) return false;
        if (seatingBank != null ? !seatingBank.equals(that.seatingBank) : that.seatingBank != null) return false;
        return !(row != null ? !row.equals(that.row) : that.row != null);
    }

    @Override
    public int hashCode() {
        int result = seatingBank != null ? seatingBank.hashCode() : 0;
        result = 31 * result + (row != null ? row.hashCode() : 0);
        result = 31 * result + seatNumber;
        return result;
    }

}
