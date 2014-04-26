package uk.co.xenonique.digital.javaee.jsfcomps;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT")
@NamedQueries({
    @NamedQuery(name="Product.findAll",
        query = "select p from Product p order by p.name"),
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PRODUCT_ID", nullable = false,
            insertable = true, updatable = true,
            table = "PRODUCT")
    private long id;
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "PRODUCT_NAME", nullable = false)
    private String name;
    @Column(name="PRODUCT_CODE", nullable = false)
    private String code;

    @Transient
    private String secretCode = Long.toString((long)(Math.random()*1.0E9));

    public Product() {
    }

    public Product(String name, String code ) {
        this.name = name;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecretCode() {
        return secretCode;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name=" + name +
            ", code=" + code +
            ", secretCode = "+secretCode  +
            "}";
    }
}

