package uk.co.xenonique.digital.cdi;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
@Named
@SessionScoped
public class ShoppingCartController implements Serializable{
    private List<LineItem> lineItems = new ArrayList<>();

    @EJB
    private MemoryDatabase database;

    @Inject
    private Utility utility;

    @PostConstruct
    public void initalise() {
        lineItems = database.defaultLineItems();
    }

    public String doList() {
        return "index.xhtml";
    }
}
