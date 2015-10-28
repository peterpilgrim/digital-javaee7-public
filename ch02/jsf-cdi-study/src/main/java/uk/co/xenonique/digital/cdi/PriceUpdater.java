package uk.co.xenonique.digital.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The type PriceUpdater
 *
 * @author Peter Pilgrim
 */
@Named
@ApplicationScoped
public class PriceUpdater {

    @Inject Event<LivePriceEvent> events;

    public void announce() {
        events.fire(new LivePriceEvent( "Digital Jave EE", Math.random() * 30 + 45.0 ));
    }

}
