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

package uk.co.xenonique.basic.mvc;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type TradeInfoServlet
 *
 * @author Peter Pilgrim
 */
@WebServlet("/tradeinfo")
public class TradeInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        final TemplateLoader loader = new ServletContextTemplateLoader(request.getServletContext());
        final Handlebars handlebars = new Handlebars(loader);
        final Template template = handlebars.compile("trade");
        final Map<String,String> context = new HashMap<String,String>() {{
            put("title", "12345FUND-EURO-FUTURE6789");
            put("detail", "Nominal 1.950250M EUR");
            put("trade-date", "23-07-2015");
            put("maturity-date", "23-07-2018");
        }};
        final String text = template.apply(context);
        response.setContentType("text/html");
        response.getOutputStream().println(text);
    }
}
