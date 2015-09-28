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

import uk.co.xenonique.digital.product.entity.UserRole;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type UserRoleService
 *
 * @author Peter Pilgrim
 */
@Stateful
public class UserRoleService {
    @PersistenceContext(unitName = "productFlow", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void add(UserRole role) {
        System.out.printf("+++++ BEFORE +++++ UserRoleService.add() role=%s, entityManager=%s\n", role, entityManager);
        entityManager.persist(role);
        entityManager.flush();
        System.out.printf("+++++ AFTER  +++++ UserRoleService.add() role=%s, entityManager=%s\n", role, entityManager);
    }

    public void update(UserRole role) {
        final UserRole userUpdated = entityManager.merge(role);
        entityManager.persist(userUpdated);
    }

    public void delete(UserRole role) {
        entityManager.remove(role);
    }

    @Remove
    public void removeConnection() {
        // TODO: Remove the implicit connection to the stateful EJB
    }

    public List<UserRole> findAll() {
        final Query query = entityManager.createNamedQuery(
            "UserRole.findAll");
        return query.getResultList();
    }

    public List<UserRole> findById(Long id) {
        final Query query = entityManager.createNamedQuery(
            "UserRole.findById").setParameter("id", id);
        return query.getResultList();
    }

    public List<UserRole> findByName(String name) {
        final Query query = entityManager.createNamedQuery(
                "UserRole.findByName").setParameter("name", name);
        return query.getResultList();
    }
}

