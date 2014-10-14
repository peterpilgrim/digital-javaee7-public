package uk.co.xenonique.digital.instant.control;

import javax.faces.component.*;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * The type WorkerBannerComponent
 *
 * @author Peter Pilgrim
 */
@FacesComponent("workerBannerComponent")
public class WorkerBannerComponent extends UINamingContainer {

    private SmartNavigation navigation;

    @Override
    public void encodeAll(FacesContext context) throws IOException {
        if (context == null) {
            throw new NullPointerException("no faces context supplied");
        }
        String sectionName = (String)getAttributes().get("sectionName");

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
