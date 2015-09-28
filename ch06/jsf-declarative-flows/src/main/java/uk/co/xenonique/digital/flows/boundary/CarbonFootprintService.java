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

package uk.co.xenonique.digital.flows.boundary;

import uk.co.xenonique.digital.flows.entity.CarbonFootprint;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type CarbonFootprintService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class CarbonFootprintService {
    @PersistenceContext(unitName = "declarativeFlowDB", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(CarbonFootprint footprint) {
        entityManager.persist(footprint);
    }

    public void update(CarbonFootprint footprint) {
        final CarbonFootprint footprintUpdated = entityManager.merge(footprint);
        entityManager.persist(footprintUpdated);
    }

    public void delete(CarbonFootprint footprint) {
        entityManager.remove(footprint);
    }

    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<CarbonFootprint> findAll() {
        Query query = entityManager.createNamedQuery(
                "CarbonFootprint.findAll");
        return query.getResultList();
    }

    public List<CarbonFootprint> findById(Long id) {
        Query query = entityManager.createNamedQuery(
                "CarbonFootprint.findById").setParameter("id", id);
        return query.getResultList();
    }
}


