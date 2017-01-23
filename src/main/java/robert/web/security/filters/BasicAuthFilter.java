package robert.web.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;

public abstract class BasicAuthFilter implements Filter {

    protected static final Logger log = Logger.getLogger(BasicAuthFilter.class);

    protected static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        try {
            doAuth(request, response);
            filterChain.doFilter(servletRequest, response);
        } catch (Throwable ignored) {
            if ( log.isDebugEnabled() ) {
                log.error("Auth failed on uri: " + request.getRequestURI());
            }
        }
    }

    abstract void doAuth(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
