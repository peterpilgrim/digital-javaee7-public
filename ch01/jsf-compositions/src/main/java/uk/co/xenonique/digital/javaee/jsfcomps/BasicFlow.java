package uk.co.xenonique.digital.javaee.jsfcomps;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * The type BasicFlow
 *
 * @author Peter Pilgrim
 */
@ManagedBean(name = "basicFlow")
@RequestScoped
public class BasicFlow {

    public String serveResponse() {
        return "endState.xhtml";
    }
}
