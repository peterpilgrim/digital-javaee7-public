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

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * The type LoginControllerFilter
 *
 * @author Peter Pilgrim
 */
@WebFilter(urlPatterns={"/protected/*", "/simple/*"})
public class LoginAuthenticationFilter implements Filter {

    private FilterConfig config;

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest request = (HttpServletRequest)req;
        final HttpServletResponse response = (HttpServletResponse)resp;
        if (request.getSession().getAttribute(LoginController.LOGIN_KEY) == null) {
            response.sendRedirect(request.getContextPath()+LoginController.LOGIN_VIEW);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.printf("**** %s.init(config=%s)\n", this.getClass().getName(), config);
        this.config = config;
    }

    public void destroy() {
        config = null;
    }
}

