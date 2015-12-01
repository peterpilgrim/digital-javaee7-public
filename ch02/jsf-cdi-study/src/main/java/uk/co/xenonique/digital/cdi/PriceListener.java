/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015,2016 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
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

package uk.co.xenonique.digital.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * The type PriceListener
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class PriceListener {
    @Inject
    private ShoppingCartController shoppingCartController;

    public void listenAndReport(@Observes LivePriceEvent event) {
        System.out.printf("event.product = %s\n", event.getProduct());
        System.out.printf("event.price = %s\n", event.getPrice());

        shoppingCartController.setImportantCustomerInformation(
                String.format("Price update: %s new price %7.2f\n", event.getProduct(), event.getPrice()));
    }
}
