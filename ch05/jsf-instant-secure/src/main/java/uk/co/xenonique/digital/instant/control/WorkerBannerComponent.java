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

    public String gettingStartedActive;
    public String yourDetailsActive;
    public String yourRateActive;
    public String completedActive;

    @Override
    public void encodeAll(FacesContext context) throws IOException {
        if (context == null) {
            throw new NullPointerException("no faces context supplied");
        }
        Object sectionNameObj = getAttributes().get("sectionName");
        String sectionName = sectionNameObj.toString();
        gettingStartedActive = yourDetailsActive =
                yourRateActive = completedActive = "";
        if ( "gettingStarted".equalsIgnoreCase(sectionName)) {
            gettingStartedActive = "active";
        }
        else if ( "yourDetails".equalsIgnoreCase(sectionName)) {
            yourDetailsActive = "active";
        }
        else if ( "yourRate".equalsIgnoreCase(sectionName)) {
            yourRateActive = "active";
        }
        else if ( "completed".equalsIgnoreCase(sectionName)) {
            completedActive = "active";
        }
        super.encodeAll(context);
    }

    // Getters and setters omitted

    public String getGettingStartedActive() {
        return gettingStartedActive;
    }

    public void setGettingStartedActive(String gettingStartedActive) {
        this.gettingStartedActive = gettingStartedActive;
    }

    public String getYourDetailsActive() {
        return yourDetailsActive;
    }

    public void setYourDetailsActive(String yourDetailsActive) {
        this.yourDetailsActive = yourDetailsActive;
    }

    public String getYourRateActive() {
        return yourRateActive;
    }

    public void setYourRateActive(String yourRateActive) {
        this.yourRateActive = yourRateActive;
    }

    public String getCompletedActive() {
        return completedActive;
    }

    public void setCompletedActive(String completedActive) {
        this.completedActive = completedActive;
    }
}
