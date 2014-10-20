package uk.co.xenonique.digital.instant.control;

import javax.faces.component.*;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * The type WorkerBannerComponent
 *
 * @author Peter Pilgrim
 */
@FacesComponent("workerBannerComponent")
public class WorkerBannerComponent extends UINamingContainer implements Serializable{

    private LendingController controller;
    private SmartNavigation navigation;

    @Override
    public void encodeAll(FacesContext context) throws IOException {
        if (context == null) {
            throw new NullPointerException("no faces context supplied");
        }
        String sectionName = (String)getAttributes().get("sectionName");
//        setSectionName(sectionName);
        controller = (LendingController)getAttributes().get("controller");
        navigation = (SmartNavigation)getAttributes().get("navigation");
        System.out.printf(">>>>> WorkerBannerComponent.encodeAll() navigation=%s\n", navigation);

//        setNavigation(navigation);

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

    public SmartNavigation getNavigation() {
        return navigation;
    }
}
