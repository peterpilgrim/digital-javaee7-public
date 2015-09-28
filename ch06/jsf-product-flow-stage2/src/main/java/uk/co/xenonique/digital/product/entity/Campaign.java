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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Campaign
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="CAMPAIGN")
@NamedQueries({
    @NamedQuery(name="Campaign.findAll",
            query = "select o from Campaign o "),
    @NamedQuery(name="Campaign.findById",
            query = "select o from Campaign o where o.id = :id order by o.creationDate"),
    @NamedQuery(name="Campaign.findByUsername",
            query = "select o from Campaign o where o.creationUser = :username"),
})
public class Campaign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="FK_CREATED_USER_ID")
    private UserProfile creationUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name="CAMPAIGN_PROMOTION",
        joinColumns={ @JoinColumn(name="FK_CAMPAIGN_ID") },
        inverseJoinColumns={ @JoinColumn(name="FK_PROMOTION_ID") }
    )
    private Set<Promotion> promotions;

    public Campaign() {
        this(null,null);
    }

    public Campaign(String title, String description) {
        this(title, description, new HashSet<Promotion>());
    }

    public Campaign(String title, String description, Set<Promotion> promotions) {
        this.title = title;
        this.description = description;
        this.promotions = promotions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(Set<Promotion> promtions) {
        this.promotions = promtions;
    }

    public UserProfile getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(UserProfile user) {
        this.creationUser = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    // equals(), hashCode(), toString() omitted

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campaign)) return false;

        Campaign campaign = (Campaign) o;

        if (id != campaign.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationUser=" + creationUser +
                ", creationDate=" + creationDate +
                ", promotions=" + promotions +
                '}';
    }
}
