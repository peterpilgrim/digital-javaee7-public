package uk.co.xenonique.digital.instant.control;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * The type DateHelperController
 *
 * @author Peter Pilgrim
 */
@Named("dateHelperController")
@ApplicationScoped
public class DateHelperController implements Serializable {
    private List<Integer> daysOfTheMonth = new ArrayList<>();
    private Map<String,Integer> monthsOfTheYear = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        for (int d=1; d<=31; ++d) {
            daysOfTheMonth.add(d);
        }

        DateFormatSymbols symbols =
                new DateFormatSymbols(Locale.getDefault());
        for (int m=1; m<=12; ++m) {
            monthsOfTheYear.put(symbols.getMonths()[m-1], m );
        }
    }

    public List<Integer> getDaysOfTheMonth() {
        return daysOfTheMonth;
    }
    public Map<String,Integer> getMonthsOfTheYear() {
        return monthsOfTheYear;
    }
}
