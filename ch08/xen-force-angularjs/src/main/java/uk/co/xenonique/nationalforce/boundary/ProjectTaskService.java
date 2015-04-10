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
public class ProjectTaskService {
    @PersistenceContext(unitName = "XenTracker")
    private EntityManager entityManager;

    public void saveProject( CaseRecord caseRecord) {
        entityManager.persist(caseRecord);
    }

    public void updateProject( CaseRecord caseRecord) {
        final CaseRecord caseRecordToBeUpdated = entityManager.merge(caseRecord);
        entityManager.persist(caseRecordToBeUpdated);
    }

    public void removeProject( CaseRecord caseRecord) {
        final CaseRecord caseRecordToBeRemoved = entityManager.merge(caseRecord);
        entityManager.remove(caseRecordToBeRemoved);
    }

    public List<CaseRecord> findAllProjects() {
        final Query query = entityManager.createNamedQuery(
            "Project.findAllProjects");
        return query.getResultList();
    }

    public List<CaseRecord> findProjectById( Integer id ) {
        final Query query = entityManager.createNamedQuery(
            "Project.findProjectById")
            .setParameter("id", id );
        return query.getResultList();
    }

    public List<Task> findTaskById( Integer id ) {
        final Query query = entityManager.createNamedQuery(
                "Project.findTaskById")
                .setParameter("id", id );
        return query.getResultList();
    }

    public List<Task> findTasksByProjectId( Integer id ) {
        final Query query = entityManager.createNamedQuery(
                "Project.findTasksByProjectId")
                .setParameter("id", id );
        return query.getResultList();
    }


    public void clearDatabase() {
        entityManager.createQuery("delete from Task").executeUpdate();
        entityManager.createQuery("delete from Project").executeUpdate();
    }
}
