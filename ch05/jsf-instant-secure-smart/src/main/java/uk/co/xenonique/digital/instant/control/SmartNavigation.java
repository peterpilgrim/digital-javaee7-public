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

package uk.co.xenonique.digital.instant.control;

import java.util.*;

/**
 * The type SmartNavigation
 *
 * @author Peter Pilgrim
 */
public class SmartNavigation {

    public void setVisitedRange(int n, int p) {
        for ( int k=0; k< elements.size(); ++k ) {
            elements.get(k).setVisited((k >= n && k <= p));
        }
    }

    private List<NavElement> elements = new ArrayList<>();

    public SmartNavigation(List<String> names) {
        for ( String name: names) {
            elements.add(new NavElement(name)) ;
        }
    }

    public SmartNavigation(Collection<NavElement> elements) {
        for ( NavElement element: elements) {
            this.elements.add(element) ;
        }
    }

    public int size() {
        return elements.size();
    }

    public List<NavElement> toList() {
        return new ArrayList<>(elements);
    }

    public NavElement get(int i) {
        return elements.get(i);
    }

    public List<NavElement> getElements() {
        return elements;
    }

    public NavElement getElementByName(String name) {
        for (NavElement element: elements) {
            if ( element.getName().equals(name)) return element;
        }
        throw new IllegalArgumentException("element not found");
    }

    public boolean hasElementByName(String name) {
        for (NavElement element: elements) {
            if ( element.getName().equals(name)) return true;
        }
        return false;
    }
}
