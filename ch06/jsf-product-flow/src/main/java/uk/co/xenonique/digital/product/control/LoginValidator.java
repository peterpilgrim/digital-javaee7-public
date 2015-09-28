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

package uk.co.xenonique.digital.product.control;

import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.UserProfile;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The type LoginValidator
 *
 * @author Peter Pilgrim
 */
@FacesValidator(value="loginValidator")
public class LoginValidator implements Validator {

    @Inject
    UserProfileService userProfileService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        final UIInput input1 = (UIInput) component.getAttributes().get("username");
        final UIInput input2 = (UIInput) component.getAttributes().get("password");
        final String username = (String) input1.getSubmittedValue();
        final String password = (String) input2.getSubmittedValue();

        final List<FacesMessage> errors = new ArrayList<>();
        final List<UserProfile> users = userProfileService.findById(username);
        if ( users.isEmpty()) {
            errors.add(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "username is unrecognised", null));
        }
        else {
            if ( !users.get(0).getPassword().equals( password )) {
                errors.add(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "invalid password", null));
            }
        }

        if ( !errors.isEmpty()) {
            throw new ValidatorException(errors);
        }
    }

}

