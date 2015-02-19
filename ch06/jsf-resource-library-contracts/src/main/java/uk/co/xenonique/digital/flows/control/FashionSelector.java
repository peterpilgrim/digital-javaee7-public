package uk.co.xenonique.digital.flows.control;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type FashionSelector
 *
 * @author Peter Pilgrim
 */
@Named
@SessionScoped
public class FashionSelector implements Serializable {
    private String theme = "default";

    public String changeTheme() {
        return "/index?faces-redirect=true";
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
