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

import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.UserProfile;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebInitParam;


import static uk.co.xenonique.digital.product.utils.AppConsts.*;

/**
 * The type LoginControllerFilter
 *
 * @author Peter Pilgrim
 */
@WebFilter(
        filterName="loginAuthenticationFilter",
        urlPatterns={"/protected/*", "/simple/*", "/compaign/*", "/approvers/*" },
        initParams={
                @WebInitParam(name = "manager", value="/simple,/campaign,/protected,/approver"),
                @WebInitParam(name = "user", value="/simple,/campaign,/protected"),
        }
)
public class LoginAuthenticationFilter implements Filter {

    @Inject
    private UserProfileService userService;
    private FilterConfig config;

    private Map<String, List<String>> rolesToDirectories = new HashMap<>();

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest request = (HttpServletRequest)req;
        final HttpServletResponse response = (HttpServletResponse)resp;
        final String loginKey = (String) request.getSession().getAttribute(LOGIN_KEY);
        System.out.printf("**** LoginAuthenticationFilter **** loginKey=[%s]\n", loginKey);
        if (loginKey == null) {
            String path = request.getRequestURI().replace(request.getContextPath(),"");
            if ( !path.equals(request.getContextPath()+LOGIN_VIEW)) {
                request.getSession().setAttribute(LAST_INPUT_PATH, path);
            }
            response.sendRedirect(request.getContextPath()+LOGIN_VIEW);
        } else {
            boolean authenticated = false;
            final List<UserProfile> users = userService.findByUsername(loginKey);
            System.out.printf("**** user=%s\n", users);
            if (!users.isEmpty()) {
                final String role = users.get(0).getRole().getName();
                if ( rolesToDirectories.containsKey(role)) {
                    final List<String> pathElements = rolesToDirectories.get(role);
                    final String inputPath = request.getRequestURI();
                    System.out.printf("**** PROCESSING **** inputPath=%s, pathElements=%s\n", inputPath, pathElements );
                    for ( String element : pathElements) {
                        if ( inputPath.contains(element)) {
                            authenticated = true;
                        }
                    }
                }
            }
            if (!authenticated) {
                response.sendRedirect(request.getContextPath()+"/login.xhtml");
            }
            else {
                chain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.printf("**** %s.init(config=%s)\n", this.getClass().getName(), config);
        this.config = config;
        for (String roleName: Collections.list(config.getInitParameterNames())) {
            final String paths = config.getInitParameter(roleName);
            final String pathElementArr[] = paths.split("[, \t]+");
            final List<String> pathElements = Arrays.asList(pathElementArr);
            System.out.printf("**** INIT **** roleName=%s, pathElements=%s\n", roleName, pathElements );

            rolesToDirectories.put(roleName,pathElements) ;
        }
    }

    public void destroy() {
        config = null;
    }
}

