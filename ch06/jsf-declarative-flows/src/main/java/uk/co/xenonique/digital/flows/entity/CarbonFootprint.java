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

package uk.co.xenonique.digital.flows.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Sector
 *
 * @author Peter Pilgrim
 */
@Entity
@Table(name = "CARBON_FOOTPRINT")
@NamedQueries({
        @NamedQuery(name="CarbonFootprint.findAll",
                query = "select c from CarbonFootprint c "),
        @NamedQuery(name="CarbonFootprint.findById",
                query = "select c from CarbonFootprint c where c.id = :id"),
})
public class CarbonFootprint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String applicationId;

    private String industryOrSector;
    // KWh (main source)
    private double electricity;
    // KWh (main source)
    private double naturalGas;
    // Litres (travel commute costs)
    private double diesel;
    // Litres (travel commute costs)
    private double petrol;


    public CarbonFootprint() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getIndustryOrSector() {
        return industryOrSector;
    }

    public void setIndustryOrSector(String industryOrSector) {
        this.industryOrSector = industryOrSector;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getNaturalGas() {
        return naturalGas;
    }

    public void setNaturalGas(double naturalGas) {
        this.naturalGas = naturalGas;
    }

    public double getDiesel() {
        return diesel;
    }

    public void setDiesel(double diesel) {
        this.diesel = diesel;
    }

    public double getPetrol() {
        return petrol;
    }

    public void setPetrol(double petrol) {
        this.petrol = petrol;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarbonFootprint carbonFootprint = (CarbonFootprint) o;

        if (id != carbonFootprint.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "CarbonFootprint{" +
                "id=" + id +
                ", applicationId='" + applicationId + '\'' +
                ", industryOrSector='" + industryOrSector + '\'' +
                ", electricity=" + electricity +
                ", naturalGas=" + naturalGas +
                ", diesel=" + diesel +
                ", petrol=" + petrol +
                '}';
    }
}
