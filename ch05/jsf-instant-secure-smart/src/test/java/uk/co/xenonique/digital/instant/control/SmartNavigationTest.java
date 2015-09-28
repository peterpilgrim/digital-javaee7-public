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

import org.junit.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies the operation of the SmartNavigationTest
 *
 * @author Peter Pilgrim
 */
public class SmartNavigationTest {

    @Test
    public void shouldInitialisedWithNotVisited() {

        List<String> tabNames = Arrays.asList("Order", "Delivery", "Payment");
        SmartNavigation nav = new SmartNavigation(tabNames);

        assertThat( nav.size(), is(3));
        for (NavElement e: nav.toList()) {
            assertThat(e.isVisited(), is(false));
        }

        for ( int j=0; j<tabNames.size(); ++j) {
            NavElement e1 = nav.get(j);
            assertThat(e1.getName(), is(tabNames.get(j)));
        }
    }


    @Test
    public void shouldSetVisitRangeOnNavigationElements() {

        List<NavElement> tabNames = Arrays.asList(
                new NavElement("Order", "order", "order.xhtml"),
                new NavElement("Finance", "finance", "finance.xhtml"),
                new NavElement("Delivery", "delivery", "delivery.xhtml"),
                new NavElement("Address", "address", "address.xhtml"),
                new NavElement("Payment", "payment", "payment.xhtml"),
                new NavElement("Confirm", "confirm", "confirm.xhtml"));
        SmartNavigation nav = new SmartNavigation(tabNames);

        final int N = 1;
        final int P = 3;

        nav.setVisitedRange(N, P);

        for ( int j=N; j<P; ++j) {
            NavElement e1 = nav.get(j);
            assertThat(e1.getName(), is(tabNames.get(j).getName()));
            assertThat(e1.getStyle(), is(tabNames.get(j).getStyle()));
            assertThat(e1.getEditLink(), is(tabNames.get(j).getEditLink()));
            assertThat(e1.isVisited(), is( j >= N && j <= P));
        }
    }


    @Test
    public void shouldRetrieveNavigationElements() {

        List<NavElement> tabNames = Arrays.asList(
                new NavElement("Order", "order", "", "order.xhtml"),
                new NavElement("Finance", "finance", "", "finance.xhtml"),
                new NavElement("Delivery", "delivery", "style1", "delivery.xhtml"),
                new NavElement("Address", "address", "", "address.xhtml"),
                new NavElement("Payment", "payment", "", "payment.xhtml"),
                new NavElement("Confirm", "confirm", "", "confirm.xhtml"));
        SmartNavigation nav = new SmartNavigation(tabNames);

        assertThat( nav.hasElementByName("Unknown"), is(false));
        assertThat( nav.hasElementByName("Order"), is(true));
        assertThat( nav.hasElementByName("Delivery"), is(true));

        NavElement element = nav.getElementByName("Delivery");
        assertThat(element.getName(), is("Delivery"));
        assertThat(element.getTitle(), is("delivery"));
        assertThat(element.getStyle(), is("style1"));
        assertThat(element.getEditLink(), is("delivery.xhtml"));
    }
}
