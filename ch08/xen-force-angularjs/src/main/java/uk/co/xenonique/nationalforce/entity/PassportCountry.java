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

package uk.co.xenonique.nationalforce.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * The type PassportCountry
 *
 * @author Peter Pilgrim
 */
@NamedQueries({
        @NamedQuery(name = "PassportCountry.findAll",
                query = "select p from PassportCountry p order by p.code"),
        @NamedQuery(name = "PassportCountry.findByCode",
                query = "select p from PassportCountry p where p.code = :code"),
        @NamedQuery(name = "PassportCountry.findByName",
                query = "select p from PassportCountry p where p.name = :name"),
})
@Entity
@Table(name="PASSPORT_COUNTRY")
public class PassportCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @NotEmpty String name;
    @NotEmpty @Size(max=3) String code;

    public PassportCountry() {
        this(null,null);
    }

    public PassportCountry(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassportCountry)) return false;

        final PassportCountry that = (PassportCountry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(code != null ? !code.equals(that.code) : that.code != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PassportCountry{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}


