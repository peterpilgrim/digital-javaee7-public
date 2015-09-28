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

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
import java.util.List;

/**
 * The type ProductDemoDataHelper
 *
 * @author Peter Pilgrim
 */
@Startup
@Singleton
public class ProductDemoDataHelper {


    @EJB ProductService productService;

    @PostConstruct
    public void initialise()
    {
        System.out.printf("***** %s.initialise() productService=%s\n", getClass().getSimpleName(), productService);
        populate();
    }

    public void populate() {
        final List<Product> products = Arrays.asList(
                new Product("Iron Widget", "useful for bolting shards", 213.98),
                new Product("Cast Steel Bolt", "useful for bolting shards", 74.23),
                new Product("Kitchen Scissors", "useful for cutting paper, strings and plastics shrink wrap", 4.99),
                new Product("Stylistic Pen", "useful for writing thoughts for the day", 4.98),
                new Product("250 A4 Sheets Paper", "writing material", 7.99),
                new Product("Traffic Cone", "Useful for controlling downtown traffic", 18.99),
                new Product("Tow truck", "Helps to move car that breaks down", 14999.99),
                new Product("Vacuum Cleaner", "perfect for keeping the humble ubode clean", 361.99),
                new Product("Plant Pot", "add a flower or plant to your garden", 14.99),
                new Product("Water Can", "Make sure you your plants plenty of water", 9.99),
                new Product("Running Shoe", "Hit the treadmill, get fitter today", 59.99),
                new Product("Track suit", "Looking good, looking sporty", 89.99)
        );

        products.forEach( product -> productService.saveProduct(product));
    }
}
