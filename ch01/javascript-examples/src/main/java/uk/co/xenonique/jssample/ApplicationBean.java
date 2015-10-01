package uk.co.xenonique.jssample;

import javax.enterprise.context.ApplicationScoped;

/**
 * The type ApplicationBean
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class ApplicationBean {

    public String getProjectName() {
        return "Digital Java EE 7";
    }

}
