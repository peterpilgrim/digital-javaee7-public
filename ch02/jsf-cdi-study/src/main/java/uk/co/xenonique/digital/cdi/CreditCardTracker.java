package uk.co.xenonique.digital.cdi;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
@ApplicationScoped
public class CreditCardTracker implements Serializable {

    public String track( String input ) {
        return "("+input + ")__TRACKED";
    }
}
