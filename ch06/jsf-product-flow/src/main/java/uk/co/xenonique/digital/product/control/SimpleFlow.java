package uk.co.xenonique.digital.product.control;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type SimpleFlow
 *
 * @author Peter Pilgrim
 */
@Named("simpleFlowController")
@FlowScoped("simple")
public class SimpleFlow implements Serializable{

    private final static String DELIMITER = " ++> ";
    private String location;
    private StringBuilder breadcrumb = new StringBuilder();


    public String getFlowScopeIdentifier() {
        return FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlow().getId();
    }

    public String beginFlow() {
        breadcrumb.append("BEGIN" + DELIMITER);
        return "/simple/page1";
    }

    public String page1() {
        breadcrumb.append("Page 1" + DELIMITER);

        return "/simple/page2";
    }

    public String page2() {
        breadcrumb.append("Page 2" + DELIMITER);

        return "/simple/page3";
    }

    public String endFlow() {
        breadcrumb.append("END" + DELIMITER);

        return "/simple-return";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StringBuilder getBreadcrumb() {
        return breadcrumb;
    }
}
