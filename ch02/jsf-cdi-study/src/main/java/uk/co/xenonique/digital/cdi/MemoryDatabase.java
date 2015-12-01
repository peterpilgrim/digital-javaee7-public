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

import javax.ejb.Startup;
import javax.ejb.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ppilgrim on 20-Oct-2015.
 */
@Singleton
@Startup
public class MemoryDatabase {

    public List<LineItem> defaultLineItems() {
        return Arrays.asList(
            new LineItem( 1200L, "Iron Widget", 49.99F, 36),
            new LineItem( 4520L, "Power-core fitness bar", 19.99F, 3),
            new LineItem( 3720L, "Cereal bar breakfast", 3.99F, 12)
            );
    }
}
