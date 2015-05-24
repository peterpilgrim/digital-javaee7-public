package uk.co.xenonique.basic.mvc;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The type User
 *
 * @author Peter Pilgrim
 */
@Named(value="user")
@RequestScoped
public class User {
    private String name;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
