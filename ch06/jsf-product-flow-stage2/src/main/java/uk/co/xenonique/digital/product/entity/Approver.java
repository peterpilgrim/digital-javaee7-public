package uk.co.xenonique.digital.product.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type ApproverEntity
 *
 * @author Peter Pilgrim
 */
@Entity
@Table(name = "APPROVER")
public class Approver implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String comment;
    private String username;

    public Approver() {
        this(null,null);
    }

    public Approver(String comment, String username) {
        this.comment = comment;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Approver approver = (Approver) o;

        if (id != approver.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Approver" +
                "@"+Integer.toHexString(System.identityHashCode(this))+
                "{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
