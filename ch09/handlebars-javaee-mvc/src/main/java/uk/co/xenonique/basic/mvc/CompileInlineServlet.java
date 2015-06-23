package uk.co.xenonique.basic.mvc;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type CompileInlineServlet
 *
 * @author Peter Pilgrim
 */
@WebServlet("/compiled")
public class CompileInlineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        final Handlebars handlebars = new Handlebars();
        final Template template = handlebars.compileInline("Hello {{this}}!");
        final String text = template.apply("Handlebars.java");
        response.setContentType("text/plain");
        response.getOutputStream().println(text);
    }
}
