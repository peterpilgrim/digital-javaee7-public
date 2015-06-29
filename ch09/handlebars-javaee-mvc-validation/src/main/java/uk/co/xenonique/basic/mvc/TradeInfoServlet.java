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
