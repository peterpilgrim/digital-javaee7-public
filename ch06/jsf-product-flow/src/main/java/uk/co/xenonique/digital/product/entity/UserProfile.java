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

/**
 * The type UserProfile.
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="USER_PROFILE")
@NamedQueries({
        @NamedQuery(name="UserProfile.findAll",
                query = "select u from UserProfile u "),
        @NamedQuery(name="UserProfile.findById",
                query = "select u from UserProfile u where u.id = :id"),
        @NamedQuery(name="UserProfile.findByUsername",
                query = "select u from UserProfile u where u.username = :username"),
})
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID", nullable = false,
            insertable = true, updatable = true,
            table = "USER_PROFILE")
    private long id;
    private String username;
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserRole role;

    public UserProfile() {
        this(null,null, null);
    }

    public UserProfile(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;

        UserProfile user = (UserProfile) o;

        if (id != user.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
