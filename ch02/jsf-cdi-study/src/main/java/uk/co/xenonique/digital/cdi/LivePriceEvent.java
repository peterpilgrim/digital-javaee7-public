package uk.co.xenonique.digital.cdi;

/**
 * The type LivePriceEvent
 *
 * @author Peter Pilgrim
 */
public class LivePriceEvent {
    private final String product;
    private final double price;

    public LivePriceEvent(String product, double price) {
        this.product = product;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LivePriceEvent)) return false;

        LivePriceEvent that = (LivePriceEvent) o;

        if (Double.compare(that.price, price) != 0) return false;
        return !(product != null ? !product.equals(that.product) : that.product != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = product != null ? product.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LivePriceEvent{");
        sb.append("product='").append(product).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
