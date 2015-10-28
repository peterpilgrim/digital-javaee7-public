package uk.co.xenonique.digital.cdi;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

/**
 * The type SecureDomain
 *
 * @author Peter Pilgrim
 */
@RequestScoped
public class SecureDomain {

    private String customerId;

    @PostConstruct
    public void initialise() {
        customerId = "client/customer/"+Integer.toHexString( (int)(Math.random() * 1e10 ));
        System.out.printf("**** %s.initialise() customerId=%s", getClass().getSimpleName(), customerId );
    }

    public String getDomain() {
        return "www.xenonique.co.uk/"+customerId;
    }
}
