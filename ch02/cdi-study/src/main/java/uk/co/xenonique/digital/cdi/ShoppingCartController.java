package uk.co.xenonique.digital.cdi;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
@SessionScoped
public class ShoppingCartController implements Serializable{
    private List<LineItem> lineItems = new ArrayList<>();

    @Inject
    private Utility utility;

    public String doList() {
        return "index.xhtml";
    }
}
