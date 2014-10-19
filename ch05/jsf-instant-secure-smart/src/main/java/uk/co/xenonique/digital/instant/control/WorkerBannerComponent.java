package uk.co.xenonique.digital.instant.control;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

/**
 * The type WorkerBannerComponent
 *
 * @author Peter Pilgrim
 */
@FacesComponent("workerBannerComponent")
public class WorkerBannerComponent extends UINamingContainer implements Serializable{

    private SmartNavigation navigation;

    @Override
    public void encodeAll(FacesContext context) throws IOException {
        if (context == null) {
            throw new NullPointerException("no faces context supplied");
        }
        String sectionName = (String)getAttributes().get("sectionName");
        navigation = (SmartNavigation)getAttributes().get("navigation");

        for (NavElement element: navigation.getElements()) {
            if ( sectionName.endsWith( element.getName())) {
                element.setStyle("active");
            }
            else {
                element.setStyle("");
            }
        }
        super.encodeAll(context);
    }

    // Getters and setters omitted


    public SmartNavigation getNavigation() {
        return navigation;
    }

    public void setNavigation(SmartNavigation navigation) {
        this.navigation = navigation;
    }

}
