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

package uk.co.xenonique.nationalforce.boundary;

import uk.co.xenonique.nationalforce.DebugConstraintViolationHelpers;
import uk.co.xenonique.nationalforce.entity.CaseRecord;
import uk.co.xenonique.nationalforce.entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ProjectTaskService
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
public class CaseRecordTaskService {
    @PersistenceContext(unitName = "XenNationalForce")
    private EntityManager entityManager;

    public void saveCaseRecord(CaseRecord caseRecord) {
        DebugConstraintViolationHelpers.execute( ()-> entityManager.persist(caseRecord) );
    }

    public void updateCaseRecord(CaseRecord caseRecord) {
        final CaseRecord caseRecordToBeUpdated = entityManager.merge(caseRecord);
        entityManager.persist(caseRecordToBeUpdated);
    }

    public void removeCaseRecord(CaseRecord caseRecord) {
        final CaseRecord caseRecordToBeRemoved = entityManager.merge(caseRecord);
        entityManager.remove(caseRecordToBeRemoved);
    }

    public List<CaseRecord> findAllCases() {
        final Query query = entityManager.createNamedQuery(
            "CaseRecord.findAllCases");
        return query.getResultList();
    }

    public List<CaseRecord> findCaseById( Integer id ) {
        final Query query = entityManager.createNamedQuery(
            "CaseRecord.findCaseById")
            .setParameter("id", id );
        return query.getResultList();
    }

    public List<Task> findTaskByTaskId( Integer id ) {
        final Query query = entityManager.createNamedQuery(
                "CaseRecord.findTaskByTaskId")
                .setParameter("id", id );
        return query.getResultList();
    }

    public List<Task> findTasksByCaseId( Integer id ) {
        final Query query = entityManager.createNamedQuery(
                "CaseRecord.findTasksByCaseId")
                .setParameter("id", id );
        return query.getResultList();
    }

    public void clearDatabase() {
        entityManager.createQuery("delete from Task").executeUpdate();
        entityManager.createQuery("delete from CaseRecord").executeUpdate();
    }
}
