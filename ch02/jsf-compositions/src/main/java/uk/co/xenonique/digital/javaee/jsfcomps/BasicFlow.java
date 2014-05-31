package uk.co.xenonique.digital.javaee.jsfcomps;

//@javax.enteprise.context.RequestScoped
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The type BasicFlow
 *
 * @author Peter Pilgrim
 */
@Named
@RequestScoped
public class BasicFlow {
    public String serveResponse() {
        return "endState.xhtml";
    }
}
