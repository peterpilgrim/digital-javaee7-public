package uk.co.xenonique.digital.cdi;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
@ApplicationScoped
public class Utility implements Serializable {

    public String debugHashCode( Object ref  ) {
        if ( ref == null) return "null";
        return String.format( "%s#%X", ref.getClass().getName(), System.identityHashCode(ref));
    }
}
