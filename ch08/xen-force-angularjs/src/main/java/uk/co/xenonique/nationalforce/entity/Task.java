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

import uk.co.xenonique.nationalforce.StringHelper;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="TASK_ID")
    private Integer id;

    @NotEmpty @Size(max=256)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name="TARGET_NAME") @Future
    private Date targetDate;

    private boolean completed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="CASE_RECORD_ID")
    private CaseRecord caseRecord;

    public Task() { /* Required by JPA */ }

    public Task(String name, Date targetDate, boolean completed) {
        this.name = name;
        this.targetDate = targetDate;
        this.completed = completed;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getTargetDate() { return targetDate; }
    public void setTargetDate(Date targetDate) { this.targetDate = targetDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public CaseRecord getCaseRecord() { return caseRecord; }
    public void setCaseRecord(CaseRecord caseRecord) { this.caseRecord = caseRecord; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", targetDate=").append(targetDate);
        sb.append(", completed=").append(completed);
        sb.append(", caseRecord=").append(StringHelper.systemIdentifierCode(caseRecord));
        sb.append('}');
        return sb.toString();
    }
}
