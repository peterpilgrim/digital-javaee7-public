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

package uk.co.xenonique.digital.product.utils;

/**
 * The type AppUtils
 *
 * @author Peter Pilgrim
 */
public final class AppUtils {

    private AppUtils() {}

    public static String systemHashIdentity( Object ref) {
        if ( ref == null) {
            return "null";
        }
        else {
            return ref.getClass().getSimpleName()+"@"+Integer.toHexString(System.identityHashCode(ref));
        }
    }
}
