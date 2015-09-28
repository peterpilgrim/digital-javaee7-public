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

package uk.co.xenonique.digital;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ContactDetailService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class ContactDetailService {
    @PersistenceContext(unitName = "applicationDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(ContactDetail contactDetail) {
        entityManager.persist(contactDetail);
    }

    public void update(ContactDetail contactDetail) {
        final ContactDetail contactDetailUpdated
                = entityManager.merge(contactDetail);
        entityManager.persist(contactDetailUpdated);
    }

    public void delete(ContactDetail contactDetail) {
        entityManager.remove(contactDetail);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<ContactDetail> findAll() {
        Query query = entityManager.createNamedQuery(
            "ContactDetail.findAll");
        return query.getResultList();
    }

    public List<ContactDetail> findById(Integer id) {
        Query query = entityManager.createNamedQuery(
            "ContactDetail.findById").setParameter("id", id);
        return query.getResultList();
    }
}

