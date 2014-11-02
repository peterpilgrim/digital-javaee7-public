package uk.co.xenonique.digital.product.entity;

import javax.persistence.*;
import java.util.List;

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
            query = "select o from Campaign o where o.id = :id"),
    @NamedQuery(name="Campaign.findByUsername",
            query = "select o from Campaign o where o.creationUser.username = :username"),
})
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;

    @OneToOne()
    @JoinColumn(name="CREATED_USER_ID")
    private UserProfile creationUser;

    @OneToMany(mappedBy="campaign", cascade = CascadeType.ALL)
    private List<Promotion> promotions;

    public Campaign() {
        this(null,null,null);
    }

    public Campaign(String title, String description, List<Promotion> promotions) {
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

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promtions) {
        this.promotions = promtions;
    }

    public UserProfile getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(UserProfile user) {
        this.creationUser = user;
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
                ", promotions=" + promotions +
                ", creationUser=" + creationUser +
                '}';
    }
}
