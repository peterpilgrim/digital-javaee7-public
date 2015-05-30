package uk.co.xenonique.basic.mvc;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
import java.util.List;

/**
 * The type ProductDemoDataHelper
 *
 * @author Peter Pilgrim
 */
@Startup
@Singleton
public class ProductDemoDataHelper {


    @EJB ProductService productService;

    @PostConstruct
    public void initialise()
    {
        System.out.printf("***** %s.initialise() productService=%s\n", getClass().getSimpleName(), productService);
        populate();
    }

    public void populate() {
        List<Product> products = Arrays.asList(
                new Product("Iron Widget", "useful for bolting shards", 213.98),
                new Product("Cast Steel Bolt", "useful for bolting shards", 74.23),
                new Product("Kitchen Scissors", "useful for cutting paper, strings and plastics shrink wrap", 4.99),
                new Product("Stylistic Pen", "useful for writing thoughts for the day", 4.98),
                new Product("250 A4 Sheets Paper", "writing material", 7.99),
                new Product("Traffic Cone", "Useful for controlling downtown traffic", 18.99),
                new Product("Tow truck", "Helps to move car that breaks down", 14999.99),
                new Product("Vacuum Cleaner", "perfect for keeping the humble ubode clean", 361.99)
        );

        products.forEach( product -> productService.saveProduct(product));
    }
}
