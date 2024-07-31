package ex.springsecurity_1805.Config;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

public class SameSiteFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Set-Cookie", "JSESSIONID=" + request.getAttribute("JSESSIONID") + "; Path=/; HttpOnly; Secure; SameSite=None");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
