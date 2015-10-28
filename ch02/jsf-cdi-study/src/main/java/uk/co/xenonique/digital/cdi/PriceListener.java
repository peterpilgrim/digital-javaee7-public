package uk.co.xenonique.digital.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * The type PriceListener
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class PriceListener {
    @Inject
    private ShoppingCartController shoppingCartController;

    public void listenAndReport(@Observes LivePriceEvent event) {
        System.out.printf("event.product = %s\n", event.getProduct());
        System.out.printf("event.price = %s\n", event.getPrice());

        shoppingCartController.setImportantCustomerInformation(
                String.format("Price update: %s new price %7.2f\n", event.getProduct(), event.getPrice()));
    }
}
