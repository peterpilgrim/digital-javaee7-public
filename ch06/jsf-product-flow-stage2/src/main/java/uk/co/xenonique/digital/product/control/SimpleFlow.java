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

    private void appendCrumb( String e) {
        if (breadcrumb.length() > 0) {
            breadcrumb.append(DELIMITER);
        }
        breadcrumb.append(e);
    }
    
    public String beginFlow() {
        appendCrumb("BEGIN");
        return "/simple/page1";
    }

    public String page1() {
        appendCrumb("Page 1");
        return "/simple/page2";
    }

    public String page2() {
        appendCrumb("Page 2");
        return "/simple/page3";
    }

    public String endFlow() {
        appendCrumb("END");
        return "/simple-return";
    }

    public String goHome() {
        appendCrumb("HOME");
        return "/index.xhtml";
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
