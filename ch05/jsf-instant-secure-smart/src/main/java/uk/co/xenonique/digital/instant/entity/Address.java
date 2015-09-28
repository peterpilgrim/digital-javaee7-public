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

/**
 * The type Address
 *
 * @author Peter Pilgrim
 */

@Entity
@Table(name="ADDRESS")
@NamedQueries({
    @NamedQuery(name="Address.findAll",
            query = "select a from Address a "),
    @NamedQuery(name="Address.findById",
            query = "select a from Address a where a.id = :id"),
})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ADDRESS", nullable = false,
            insertable = true, updatable = true,
            table = "ADDRESS")
    private long id;

    String houseOrFlatNumber;
    String street1;
    String street2;
    String townOrCity;
    String region;
    String areaCode;
    String country;

    // toString(), hashCode(), equalsTo() elided

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", houseOrFlatNumber='" + houseOrFlatNumber + '\'' +
                ", street1='" + street1 + '\'' +
                ", street2='" + street2 + '\'' +
                ", townOrCity='" + townOrCity + '\'' +
                ", region='" + region + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (id != address.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    // Getters and setters elided
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHouseOrFlatNumber() {
        return houseOrFlatNumber;
    }

    public void setHouseOrFlatNumber(String houseOrFlatNumber) {
        this.houseOrFlatNumber = houseOrFlatNumber;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getTownOrCity() {
        return townOrCity;
    }

    public void setTownOrCity(String townOrCity) {
        this.townOrCity = townOrCity;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
