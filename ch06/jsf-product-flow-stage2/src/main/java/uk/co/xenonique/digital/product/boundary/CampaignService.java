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

package uk.co.xenonique.digital.product.boundary;

import uk.co.xenonique.digital.product.entity.Campaign;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type CampaignService
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class CampaignService {
    @PersistenceContext(unitName = "productFlow", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(Campaign obj) {
        entityManager.persist(obj);
    }

    public void update(Campaign obj) {
        final Campaign objUpdated = entityManager.merge(obj);
        entityManager.persist(objUpdated);
    }

    public void delete(Campaign obj) {
        entityManager.remove(obj);
    }

    @Remove
    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<Campaign> findAll() {
        final Query query = entityManager.createNamedQuery(
                "Campaign.findAll");
        return query.getResultList();
    }

    public List<Campaign> findById(Long id) {
        final Query query = entityManager.createNamedQuery(
                "Campaign.findById").setParameter("id", id);
        return query.getResultList();
    }

    public List<Campaign> findByUsername(String username) {
        final Query query = entityManager.createNamedQuery(
                "Campaign.findByUsername").setParameter("username", username);
        return query.getResultList();
    }

}
