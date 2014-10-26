package uk.co.xenonique.digital.instant.control;

/**
 * The type ViewScopedBean
 *
 * @author Peter Pilgrim (peter)
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Peter Pilgrim
 * @author John Yeary
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class ViewScopedBean implements Serializable {

    private static final long serialVersionUID = 3152248670694285690L;
    private List<String> names;

    public ViewScopedBean() {
        names = new ArrayList<String>();
    }

    @PostConstruct
    private void initialize() {
        names.add("John");
        names.add("Ethan");
        names.add("Sean");
        names.add("Patty");
        names.add("Java");
        names.add("Duke");
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void delete(String name) {
        names.remove(name);
    }
}