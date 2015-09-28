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

package uk.co.xenonique.payments;

import java.util.HashSet;
import java.util.Set;

/**
 * The type PaymentIssuerImpl
 *
 * @author Peter Pilgrim
 */
public class PaymentIssuerImpl implements PaymentIssuer {

    private Set<Allocation> allocations = new HashSet<>();

    @Override
    public void allocate(Allocation allocation) {
        allocations.add(allocation);
    }


    public Set<Allocation> getAllocations() {
        return allocations;
    }

}
