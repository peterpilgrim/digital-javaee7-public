/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013,2014 by Peter Pilgrim, Addiscombe, Surrey, XeNoNiQUe UK
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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type CaseRecord
 *
 * @author Peter Pilgrim (peter)
 */
@NamedQueries({
    @NamedQuery(name="CaseRecord.findAllCases",
        query = "select c from CaseRecord c order by c.lastName, c.firstName"),
    @NamedQuery(name="CaseRecord.findCaseById",
        query = "select c from CaseRecord c where c.id = :id"),
    @NamedQuery(name="CaseRecord.findTaskByTaskId",
        query = "select t from Task t where t.id = :id "),
    @NamedQuery(name="CaseRecord.findTasksByCaseId",
        query = "select t from CaseRecord c, Task t " +
                "where c.id = :id and t.caseRecord = c"),
})
@Entity
@Table(name = "CASE_RECORD")
public class CaseRecord {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty @Size(max=64) private String lastName;
    @NotEmpty @Size(max=64) private String firstName;
    @NotEmpty @Size(max=1) private String sex;

    /** Reasonable Length of passport number http://www.highprogrammer.com/alan/numbers/mrp.html */
    @NotEmpty @Size(max=16) private String passportNo;
    @NotEmpty @Size(max=32) private String country;

    @Past @NotNull @Temporal(TemporalType.DATE) private Date dateOfBirth;
    @Future @NotNull @Temporal(TemporalType.DATE) private Date expirationDate;

    @NotEmpty private String currentState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caseRecord",
        fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    public CaseRecord() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public boolean addTask( Task task ) {
        if ( ! tasks.contains( task) ) {
            CaseRecord oldCaseRecord = task.getCaseRecord();
            if ( oldCaseRecord != null ) {
                removeTask( task );
            }
            tasks.add(task);
            task.setCaseRecord(this);
            return true;
        } else { return false; }
    }

    public boolean removeTask( Task task ) {
        if ( tasks.contains( task) ) {
            tasks.remove(task);
            task.setCaseRecord(null);
            return true;
        } else { return false; }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaseRecord)) return false;

        CaseRecord that = (CaseRecord) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (passportNo != null ? !passportNo.equals(that.passportNo) : that.passportNo != null) return false;
        return !(country != null ? !country.equals(that.country) : that.country != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (passportNo != null ? passportNo.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CaseRecord{");
        sb.append("id=").append(id);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", passportNo='").append(passportNo).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append(", expirationDate=").append(expirationDate);
        sb.append(", currentState=").append(currentState);
        sb.append(", tasks=").append(tasks);
        sb.append('}');
        return sb.toString();
    }
}
