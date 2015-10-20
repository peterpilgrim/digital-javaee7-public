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
