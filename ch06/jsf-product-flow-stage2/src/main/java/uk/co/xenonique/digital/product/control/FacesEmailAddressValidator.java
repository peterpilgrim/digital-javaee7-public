package uk.co.xenonique.digital.product.control;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type FacesEmailAddressValidator
 *
 * @author Peter Pilgrim
 */
@FacesValidator("emailValidator")
public class FacesEmailAddressValidator implements Validator {
    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        final String text = value.toString();
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);
        final Matcher matcher = pattern.matcher(text);
        if ( !matcher.matches() ) {
            throw new ValidatorException(
                    new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "{application.emailAddress.pattern}",
                "The value must be a valid email address."));
        }
    }
}
