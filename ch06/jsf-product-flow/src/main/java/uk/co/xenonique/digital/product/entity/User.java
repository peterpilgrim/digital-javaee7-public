package uk.co.xenonique.digital.product.entity;

import javax.persistence.*;

/**
 * The type User
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="USER")
@NamedQueries({
        @NamedQuery(name="User.findAll",
                query = "select u from User u "),
        @NamedQuery(name="User.findById",
                query = "select u from User u where u.id = :id"),
        @NamedQuery(name="User.findByUsername",
                query = "select u from User u where u.username = :username"),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER", nullable = false,
            insertable = true, updatable = true,
            table = "USER")
    private long id;
    private String username;
    private String password;

    public User() {
        this(null,null);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
