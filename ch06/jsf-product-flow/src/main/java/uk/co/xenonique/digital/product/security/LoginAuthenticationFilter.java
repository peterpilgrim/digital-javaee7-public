package uk.co.xenonique.digital.product.security;

import uk.co.xenonique.digital.product.control.LoginController;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * The type LoginControllerFilter
 *
 * @author Peter Pilgrim
 */
public class LoginAuthenticationFilter implements Filter {

    private FilterConfig config;

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest request = (HttpServletRequest)req;
        final HttpServletResponse response = (HttpServletResponse)resp;
        if (request.getSession().getAttribute(LoginController.LOGIN_KEY) == null) {
            response.sendRedirect(request.getContextPath()+"/index.xhtml");
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

