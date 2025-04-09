package security.springsecurityinaction.filter;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.web.csrf.CsrfToken;

import java.io.IOException;

public class CsrfTokenLogger implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CsrfToken o = (CsrfToken) servletRequest.getAttribute("_csrf");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
