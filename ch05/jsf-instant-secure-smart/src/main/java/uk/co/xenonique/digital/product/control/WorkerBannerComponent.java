package uk.co.xenonique.digital.product.control;

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

    private NavigatableController controller;

    @Override
    public void encodeAll(FacesContext context) throws IOException {
        if (context == null) {
            throw new NullPointerException("no faces context supplied");
        }
        String sectionName = (String)getAttributes().get("sectionName");
//        setSectionName(sectionName);
        controller = (NavigatableController)getAttributes().get("controller");
        SmartNavigation navigation = controller.getNavigation();
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

}
