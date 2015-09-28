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

package uk.co.xenonique.digital;

import javax.persistence.*;
import java.util.Date;

/**
 * The type ContactDetail
 *
 * @author Peter Pilgrim
 */
@Entity
@Table(name="CONTACT")
@NamedQueries({
    @NamedQuery(name="ContactDetail.findAll",
        query = "select c from ContactDetail c " +
                "order by c.lastName, c.middleName, c.firstName"),
    @NamedQuery(name="ContactDetail.findAllOrderByEmail",
        query = "select c from ContactDetail c order by c.email"),
    @NamedQuery(name="ContactDetail.findById",
        query = "select c from ContactDetail c where c.id = :id"),
})
public class ContactDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CONTACT_ID", nullable = false,
            insertable = true, updatable = true,
            table = "CONTACT")
    private long id;

    private String title="";
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private Boolean allowEmails;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) {
        this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email; }

    public Boolean getAllowEmails() { return allowEmails; }
    public void setAllowEmails(Boolean allowEmails) {
        this.allowEmails = allowEmails; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactDetail)) return false;
        ContactDetail that = (ContactDetail) o;
        if (id != that.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "ContactDetail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", allowEmails=" + allowEmails +
                '}';
    }
}
