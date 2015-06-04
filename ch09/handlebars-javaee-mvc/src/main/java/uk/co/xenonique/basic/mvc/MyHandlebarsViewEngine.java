package uk.co.xenonique.basic.mvc;

/**
 * The type AppHandlebarsViewEngine
 *
 * @author Peter Pilgrim
 */
import com.github.jknack.handlebars.Handlebars;
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
import java.util.stream.Collectors;


/**
 * Class HandlebarsViewEngine
 *
 * @author Rahman Usta
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
             BufferedReader bufferedReader = new BufferedReader(in);) {

            final String viewContent = bufferedReader.lines().collect(Collectors.joining());

            final TemplateLoader loader = new ServletContextTemplateLoader(servletContext);
            final Handlebars handlebars = new Handlebars(loader);
            final Template template = handlebars.compileInline(viewContent);
            template.apply(models, writer);
        } catch (IOException e) {
            throw new ViewEngineException(e);
        }
    }
}
