package uk.co.xenonique.digital.product.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Peter Pilgrim
 * @author John Yeary
 * @version 1.0
 */
@Named
@ManagedBean
@ConversationScoped
public class ConversationBean implements Serializable {

    private static final long serialVersionUID = 5649508572742937677L;
    private List<String> names;
    @Inject
    private Conversation conversation;

    public ConversationBean() {
        names = new ArrayList<String>();
    }

    @PostConstruct
    private void initialize() {
        if ( conversation.isTransient()) {
            conversation.begin();
        }
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

    public String destroy() {
        conversation.end();
        return "index";
    }

    public void destroy(ActionEvent event) {
        conversation.end();
    }
}