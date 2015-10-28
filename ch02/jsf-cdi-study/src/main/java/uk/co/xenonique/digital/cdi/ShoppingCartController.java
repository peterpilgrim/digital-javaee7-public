package uk.co.xenonique.digital.cdi;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
@Named
@SessionScoped
public class ShoppingCartController implements Serializable{
    private List<LineItem> lineItems = new ArrayList<>();

    private String importantCustomerInformation;

    private String debugMessage;

    @EJB
    private MemoryDatabase database;

    @Inject
    private Utility utility;

    @Inject
    private SecureDomain secureDomain;

    @PostConstruct
    public void initialise() {
        lineItems = database.defaultLineItems();
    }

    public String doList() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.printf("%s.doOrder()\n", this.getClass().getSimpleName());
        pw.printf("  shoppingCartController = %s\n", utility.debugHashCode( this ));
        pw.printf("  database = %s\n", utility.debugHashCode( database ));
        pw.printf("  utility = %s\n", utility.debugHashCode(utility));
        pw.printf("  secureDomain = %s\n", utility.debugHashCode(secureDomain));
        pw.printf("  secureDomain.domain = %s\n", secureDomain.getDomain());
        debugMessage = sw.toString();
        return "index.xhtml";
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String doOrder() {
        return "order.xhtml";
    }

    public String getImportantCustomerInformation() {
        return importantCustomerInformation;
    }

    public void setImportantCustomerInformation(String importantCustomerInformation) {
        this.importantCustomerInformation = importantCustomerInformation;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
