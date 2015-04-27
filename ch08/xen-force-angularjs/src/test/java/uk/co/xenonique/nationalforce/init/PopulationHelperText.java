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
public class PopulationHelperText {

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
        checkRandomNames(N, 0.5,  ()-> helper.getRandomSurname());
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
        assertThat(firstNames.size(), is(greaterThan((int)(count * ratio))));
    }

    private void checkName(String name) {
        assertThat(name, is(notNullValue()));
        assertThat(name.trim().length(), is(greaterThan(0)));
        for (int j=0; j<name.length(); ++j) {
            assertThat(Character.isAlphabetic(name.charAt(j)), is(true));
        }
    }
}
