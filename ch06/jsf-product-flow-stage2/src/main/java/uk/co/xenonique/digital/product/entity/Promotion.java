package uk.co.xenonique.digital.product.entity;

import javax.persistence.*;
import java.util.List;

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
            query = "select p from Promotion p where p.id = :id"),
})
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String headline;
    private String description;
    private boolean approved;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserProfile> approvers;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIGN_ID")
    private Campaign campaign;

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

    public List<UserProfile> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<UserProfile> approvers) {
        this.approvers = approvers;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
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
                '}';
    }
}

