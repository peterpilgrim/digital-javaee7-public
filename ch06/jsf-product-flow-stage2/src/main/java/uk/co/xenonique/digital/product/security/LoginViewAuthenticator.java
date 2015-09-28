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

package uk.co.xenonique.digital.product.security;

import uk.co.xenonique.digital.product.control.LoginController;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import static uk.co.xenonique.digital.product.utils.AppConsts.*;

/**
 * The type PreRenderViewAuthenticator
 *
 * @author Peter Pilgrim
 */
@Named("loginViewAuthenticator")
@ApplicationScoped()
public class LoginViewAuthenticator {

    @PostConstruct
    public void init() {
        System.out.printf("**** %s.init()\n", this.getClass().getName() );
    }

    public void check() {
        check(null);
    }

    public void check(String lastInputPath) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        final String currentUser = (String) session.getAttribute(LOGIN_KEY);
        if (currentUser == null || currentUser.length() == 0) {
            if ( lastInputPath != null ) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(LAST_INPUT_PATH, lastInputPath);
            }
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(facesContext, null, LOGIN_VIEW);
        }
    }
}