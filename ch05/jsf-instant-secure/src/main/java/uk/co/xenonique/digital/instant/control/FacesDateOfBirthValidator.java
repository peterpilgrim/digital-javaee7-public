/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package uk.co.xenonique.digital.instant.control;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The type FacesDateOfBirthValidator
 *
 * @author Peter Pilgrim
 */
@FacesValidator("dateOfBirthValidator")
public class FacesDateOfBirthValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
    throws ValidatorException {
        final UIInput dayComp   = (UIInput)component.getAttributes().get("dob_dotm");
        final UIInput monthComp = (UIInput)component.getAttributes().get("dob_moty");
        final UIInput yearComp  = (UIInput)component.getAttributes().get("dob_year");

        final List<FacesMessage> errors = new ArrayList<>();
        final int day = parsePositiveInteger(dayComp.getSubmittedValue());
        if ( day < 1 || day > 31 ) {
            errors.add(new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "DOB day must be in the range of 1 to 31 ", null ));
        }
        final int month = parsePositiveInteger(monthComp.getSubmittedValue());
        if ( month < 1 || month > 12 ) {
            errors.add(new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "DOB month must be in the range of 1 to 12 ", null));
        }

        final Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -18);
        final Date eighteenBirthday = cal.getTime();

        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -100);
        final Date hundredthBirthday = cal.getTime();

        final int year = parsePositiveInteger(yearComp.getSubmittedValue());
        cal.set(year,month,day);
        final Date targetDate = cal.getTime();
        if (targetDate.after(eighteenBirthday) ) {
            errors.add(new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "DOB year: you must be 18 years old.", null));
        }
        if ( targetDate.before(hundredthBirthday)) {
            errors.add(new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "DOB year: you must be younger than 100 years old.", null ));
        }
        if ( !errors.isEmpty()) {
            throw new ValidatorException(errors);
        }
    }

    public int parsePositiveInteger( Object value ) {
        if ( value == null ) return -1;
        try {
            return Integer.parseInt( value.toString().trim());
        }
        catch (NumberFormatException nfe) {
            return -1;
        }
    }
}
