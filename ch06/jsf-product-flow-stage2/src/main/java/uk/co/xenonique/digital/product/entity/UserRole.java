package uk.co.xenonique.digital.product.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type UserRole
 *
 * @author Peter Pilgrim
 */
@Entity
@Table(name = "USER_ROLE")
@NamedQueries({
        @NamedQuery(name="UserRole.findAll",
                query = "select u from UserRole u "),
        @NamedQuery(name="UserRole.findById",
                query = "select u from UserRole u where u.id = :id"),
        @NamedQuery(name="UserRole.findBName",
                query = "select u from UserRole u where u.name = :uname"),
})
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    public UserRole() {
        this(null);
    }

    public UserRole(String name) {
        this.name = name;
    }

    public UserRole(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole role = (UserRole) o;

        if (id != role.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "UserRole" +
                "@"+Integer.toHexString(System.identityHashCode(this))+
                "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
