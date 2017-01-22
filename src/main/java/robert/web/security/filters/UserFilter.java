package robert.web.security.filters;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserFilter extends BasicAuthFilter {

    private static final Logger log = Logger.getLogger(UserFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Logger initiated");
    }

    @Override
    public void destroy() {
        log.info("Logger destroyed");
    }

    @Override
    void doAuth(HttpServletRequest request, HttpServletResponse response) {
        if ( isUriPublic(request.getRequestURI()) )
            return;

    }

    private boolean isUriPublic(String requestURI) {
        for (String uriPattern : Validation.PUBLIC_URIS) {
            if ( matcher.match(uriPattern, requestURI) )
                return true;
        }
        return false;
    }
}
