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

package uk.co.xenonique.digital.product.entity;

import static uk.co.xenonique.digital.product.utils.AppUtils.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Promotion
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="PROMOTION")
@NamedQueries({
    @NamedQuery(name="Promotion.findAll",
            query = "select p from Promotion p "),
    @NamedQuery(name="Promotion.findById",
            query = "select p from Promotion p where p.id = :id order by p.creationDate"),
})
public class Promotion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String headline;
    private String description;
    private boolean approved;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name="PROMOTION_APPROVER",
        joinColumns={ @JoinColumn(name="FK_PROMOTION_ID") },
        inverseJoinColumns={ @JoinColumn(name="FK_APPROVER_ID") }
    )
    private Set<Approver> approvers = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CAMPAIGN_ID")
    private Campaign campaign;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    public Promotion() {
        this(null,null);
    }

    public Promotion(String headline, String description) {
        this.headline = headline;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String title) {
        this.headline = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Set<Approver> getApprovers() {
        return approvers;
    }

    public void setApprovers(Set<Approver> approvers) {
        this.approvers = approvers;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String delimitedApprovers() {
        StringBuilder buf = new StringBuilder();
        boolean  first=true;
        for ( Approver approver: approvers) {
            if (first) {
                first =false;
            }
            else {
                buf.append(", ");
            }
            buf.append(approver.getUser().getUsername());
        }
        return buf.toString();
    }

// equals(), hashCode(), toString() omitted

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promotion)) return false;

        Promotion promotion = (Promotion) o;

        if (id != promotion.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", headline='" + headline + '\'' +
                ", description='" + description + '\'' +
                ", approved=" + approved +
                ", approvers=" + approvers +
                ", campaign=" + systemHashIdentity(campaign) +
                ", creationDate=" + creationDate +
                '}';
    }
}

