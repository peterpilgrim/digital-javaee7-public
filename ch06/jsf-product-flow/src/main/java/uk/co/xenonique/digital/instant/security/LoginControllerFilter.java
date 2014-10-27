package uk.co.xenonique.digital.instant.security;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * The type LoginControllerFilter
 *
 * @author Peter Pilgrim
 */
public class LoginControllerFilter implements Filter {

    private FilterConfig config;

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        if (request.getSession().getAttribute(AuthenticationBean.AUTH_KEY) == null) {
            response.sendRedirect("../index.xhtml");
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {
        config = null;
    }
}
}
