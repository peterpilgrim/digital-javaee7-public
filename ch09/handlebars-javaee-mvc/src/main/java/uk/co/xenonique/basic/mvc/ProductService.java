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

package uk.co.xenonique.basic.mvc;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The type ProductService
 *
 * @author Peter Pilgrim
 */
@Stateless
public class ProductService {

    @PersistenceContext(unitName = "HandlebarsMvcDemo")
    private EntityManager entityManager;

    public void saveProduct(Product product) {
        System.out.println("**** saveProduct() product="+product);
        entityManager.persist(product);
    }

    public void updateProduct(Product product) {
        final Product productToBeUpdated = entityManager.merge(product);
        entityManager.persist(productToBeUpdated);
    }

    public void removeProduct(Product product) {
        final Product productToBeRemoved = entityManager.merge(product);
        entityManager.remove(productToBeRemoved);
    }

    public List<Product> findAll() {
        final Query query = entityManager.createNamedQuery("Product.findAll");
        return query.getResultList();
    }

    public List<Product> findById(Integer id) {
        final Query query = entityManager.createNamedQuery("Product.findById").setParameter("id", id);
        return query.getResultList();
    }

}