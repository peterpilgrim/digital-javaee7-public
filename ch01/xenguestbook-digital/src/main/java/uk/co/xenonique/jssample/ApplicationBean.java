package uk.co.xenonique.jssample;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * The type ApplicationBean
 *
 * @author Peter Pilgrim
 */
@Named
@ApplicationScoped
public class ApplicationBean {

    public String getProjectName() {
        return "Digital Java EE 7";
    }

}
