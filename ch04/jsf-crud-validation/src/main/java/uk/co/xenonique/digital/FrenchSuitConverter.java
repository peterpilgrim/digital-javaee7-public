package uk.co.xenonique.digital;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * The type FrenchSuitConverter
 *
 * @author Peter Pilgrim
 */
@FacesConverter
public class FrenchSuitConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return null;
    }
}
