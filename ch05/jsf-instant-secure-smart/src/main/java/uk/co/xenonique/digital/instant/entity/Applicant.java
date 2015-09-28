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

package uk.co.xenonique.digital.instant.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The type Applicant
 *
 * @author Peter Pilgrim
 */
@Entity
@Table(name="APPLICANT")
@NamedQueries({
    @NamedQuery(name="Applicant.findAll",
            query = "select a from Applicant a " +
                    "order by a.submitDate"),
    @NamedQuery(name="Applicant.findById",
            query = "select a from Applicant a where a.id = :id"),
})
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactDetail  contactDetail;
    @OneToOne(cascade = CascadeType.ALL)
    private Address  address;

    private String workPhone;
    private String homePhone;
    private String mobileNumber;

    private BigDecimal loanAmount;
    private BigDecimal loanRate;
    private int loanTermMonths;
    private boolean termsAgreed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;

    public Applicant() {
        this(null);
    }

    public Applicant(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }


    // toString(), hashCode(), equalsTo() elided

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", contactDetail=" + contactDetail +
                ", address=" + address +
                ", workPhone='" + workPhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", loanAmount=" + loanAmount +
                ", loanRate=" + loanRate +
                ", loanTermMonths=" + loanTermMonths +
                ", termsAgreed=" + termsAgreed +
                ", submitDate=" + submitDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Applicant)) return false;

        Applicant applicant = (Applicant) o;

        if (id != applicant.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    // Getters and setter elided


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public int getLoanTermMonths() {
        return loanTermMonths;
    }

    public void setLoanTermMonths(int loanTermMonths) {
        this.loanTermMonths = loanTermMonths;
    }

    public boolean isTermsAgreed() {
        return termsAgreed;
    }

    public void setTermsAgreed(boolean tocAgreed) {
        this.termsAgreed = tocAgreed;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
