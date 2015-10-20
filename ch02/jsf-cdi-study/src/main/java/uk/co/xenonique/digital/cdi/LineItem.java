package uk.co.xenonique.digital.cdi;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
public class LineItem {
    private long id;
    private String productName;
    private float price;
    private int quantity;

    public LineItem() {
    }

    public LineItem(long id, String productName, float price, int quantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LineItem lineItem = (LineItem) o;

        return id == lineItem.id;

    }

    @Override public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override public String toString() {
        return "LineItem{" +
            "id=" + id +
            ", productName='" + productName + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
    }
}
