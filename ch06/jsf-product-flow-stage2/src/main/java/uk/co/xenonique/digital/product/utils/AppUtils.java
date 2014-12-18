package uk.co.xenonique.digital.product.utils;

/**
 * The type AppUtils
 *
 * @author Peter Pilgrim
 */
public final class AppUtils {

    private AppUtils() {}

    public static String systemHashIdentity( Object ref) {
        if ( ref == null) {
            return "null";
        }
        else {
            return ref.getClass().getSimpleName()+"@"+Integer.toHexString(System.identityHashCode(ref));
        }
    }
}
