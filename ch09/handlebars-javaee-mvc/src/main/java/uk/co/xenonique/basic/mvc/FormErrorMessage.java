package uk.co.xenonique.basic.mvc;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The type FormErrorMessage
 *
 * @author Peter Pilgrim
 */

@Named("error")
@RequestScoped
public class FormErrorMessage {
    private String property;
    private Object value;
    private String message;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
