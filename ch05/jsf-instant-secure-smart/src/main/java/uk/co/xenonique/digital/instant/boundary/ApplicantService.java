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

package uk.co.xenonique.digital.instant.boundary;

import uk.co.xenonique.digital.instant.entity.Applicant;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ApplicantService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class ApplicantService {
    @PersistenceContext(unitName = "instantLendingDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(Applicant applicant) {
        entityManager.persist(applicant);
    }

    public void update(Applicant applicant) {
        final Applicant applicantUpdated
                = entityManager.merge(applicant);
        entityManager.persist(applicantUpdated);
    }

    public void delete(Applicant applicant) {
        entityManager.remove(applicant);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<Applicant> findAll() {
        Query query = entityManager.createNamedQuery(
            "Applicant.findAll");
        return query.getResultList();
    }

    public List<Applicant> findById(Integer id) {
        Query query = entityManager.createNamedQuery(
            "Applicant.findById").setParameter("id", id);
        return query.getResultList();
    }
}

