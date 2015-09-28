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

/**
 * The type AppHandlebarsViewEngine
 *
 * @author Peter Pilgrim
 */

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.oracle.ozark.engine.ViewEngineBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.ServletContext;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.stream.Collectors;


/**
 * Class HandlebarsViewEngine
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class MyHandlebarsViewEngine extends ViewEngineBase {

    @Inject
    private ServletContext servletContext;

    @Override
    public boolean supports(String view) {
        return view.endsWith(".hbs") || view.endsWith(".handlebars");
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {
        final Models models = context.getModels();
        final String viewName = context.getView();

        try (PrintWriter writer = context.getResponse().getWriter();
             InputStream resourceAsStream = servletContext.getResourceAsStream(resolveView(context));
             InputStreamReader in = new InputStreamReader(resourceAsStream, "UTF-8");
             BufferedReader buffer = new BufferedReader(in);) {

            final String viewContent = buffer.lines().collect(Collectors.joining());

            final TemplateLoader loader = new ServletContextTemplateLoader(servletContext);
            final Handlebars handlebars = new Handlebars(loader);

            models.put("webContextPath", context.getRequest().getContextPath());
            models.put("page", context.getRequest().getRequestURI());
            models.put("viewName", viewName);
            models.put("request", context.getRequest());
            models.put("response", context.getResponse());

            handlebars.registerHelper("formatDecimal", new Helper<BigDecimal>() {
                @Override
                public CharSequence apply(BigDecimal number, Options options) throws IOException {
                    final DecimalFormat formatter = new DecimalFormat("###0.##");
                    return formatter.format(number.doubleValue());
                }
            });
            final Template template = handlebars.compileInline(viewContent);
            template.apply(models, writer);
        } catch (IOException e) {
            throw new ViewEngineException(e);
        }
    }
}
