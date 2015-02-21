package uk.co.xenonique.digital.product.boundary;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

/**
 * The type ProductFlowDatabase
 *
 * @author Peter Pilgrim
 */
@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, METHOD})
public @interface ProductFlowDatabase {
}
