package uk.co.xenonique.digital.product.control;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.security.Principal;

/**
 * The type InfoSecurityComponent
 *
 * @author Peter Pilgrim
 */
@FacesComponent(
    value="information",
    namespace = "http:/www.xenonique.co.uk/jsf/instant/lending",
    tagName = "infoSec", createTag = true)
public class InfoSecurityComponent extends UINamingContainer {
    private String message;

    @Override
    public String getFamily() {
        return "instant.lending.custom.component";
    }

    @Override
    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = message;
        return ((Object) (values));
    }

    @Override
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        message = (String) values[1];
    }

    public void encodeBegin(FacesContext context)
            throws IOException {
        ResponseWriter writer =
                context.getResponseWriter();
        writer.startElement("div", this);
        writer.writeAttribute("role", "alert", null );
        Principal principal = FacesContext.getCurrentInstance()
            .getExternalContext().getUserPrincipal();
        String name;
        if ( principal !=null ) {
            writer.writeAttribute("class","alert  alert-success",null);
            name = principal.getName();
        }
        else {
            writer.writeAttribute("class","alert  alert-danger",null);
            name = "unknown";
        }
        writer.write(
            String.format("[USER: <strong>%s</strong>] - %s",
            name, message));
    }

    public void encodeEnd(FacesContext context)
            throws IOException {
        ResponseWriter writer =
                context.getResponseWriter();
        writer.endElement("div");
        writer.flush();
    }

    // Getters and setter omitted
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
