package uk.co.xenonique.digital.javaee.jsfcomps;

import javax.ejb.*;

@Stateless
public class ServiceDesk {

    public String hello( String name ) {
        return "Hello "+name;
    }

}

