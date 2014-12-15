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
