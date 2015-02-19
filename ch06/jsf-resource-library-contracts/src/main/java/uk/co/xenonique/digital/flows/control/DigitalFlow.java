package uk.co.xenonique.digital.flows.control;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type DigitalFlow
 *
 * @author Peter Pilgrim
 */
@Named
@FlowScoped("digitalFlow")
public class DigitalFlow implements Serializable {

    public String debugClassName() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString( System.identityHashCode(this));
    }

    public String gotoPage1() {
        return "digitalFlow.xhtml";
    }

    public String gotoPage2() {
        return "digitalFlow-p2.xhtml";
    }

    public String gotoPage3() {
        return "digitalFlow-p3.xhtml";
    }

    public String gotoPage4() {
        return "digitalFlow-p4.xhtml";
    }

    public String gotoEndFlow() {
        return "/digitalFlow-return.xhtml";
    }

}
