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

package uk.co.xenonique.nationalforce.init;

import java.util.*;
import java.util.function.Supplier;

import static org.junit.Assert.*;

import org.junit.*;
import uk.co.xenonique.nationalforce.entity.PassportCountry;

import static org.hamcrest.Matchers.*;


/**
 * Verifies the operation of the PopulationHelperText
 *
 * @author Peter Pilgrim
 */
public class PopulationHelperTest {

    @Test
    public void generate_first_names() {
        final PopulationHelper helper = new PopulationHelper();
        int N = 50;
        checkRandomNames(N, 0.5, ()-> helper.getRandomGirlsName());
    }

    @Test
    public void generate_last_names() {
        final PopulationHelper helper = new PopulationHelper();
        int N = 50;
        checkRandomNames(N, 0.5, ()-> helper.getRandomBoysName());
    }

    @Test
    public void generate_surnames() {
        final PopulationHelper helper = new PopulationHelper();
        int N = 50;
        checkRandomNames(N, 0.35,  ()-> helper.getRandomSurname());
    }

    @Test
    public void generate_random_country() {
        final PopulationHelper helper = new PopulationHelper();
        int N = 67;
        Set<PassportCountry> countries = new HashSet<>();
        for (int k=0; k< N; ++k) {
            final PassportCountry country = helper.getRandomPassportCountry();
            countries.add(country);
        }
        assertThat(countries.size(), is(greaterThan((int) (N * 0.6667))));
    }

    private void checkRandomNames(int count, double ratio, Supplier<String> supplier) {
        Set<String> firstNames = new HashSet<>();
        for (int k=0; k< count; ++k) {
            final String name = supplier.get();
            checkName(name);
            firstNames.add(name);
        }
        assertThat(firstNames.size(), is(greaterThanOrEqualTo((int) (count * ratio))));
    }

    private void checkName(String name) {
        assertThat(name, is(notNullValue()));
        assertThat(name.trim().length(), is(greaterThan(0)));
        for (int j=0; j<name.length(); ++j) {
            char ch = name.charAt(j);
            if ( ch != '-' &&  ch != ' ') {
                assertThat(Character.isAlphabetic(ch), is(true));
            }
        }
    }
}
