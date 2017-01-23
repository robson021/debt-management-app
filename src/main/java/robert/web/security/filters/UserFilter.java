package robert.web.security.filters;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import robert.web.session.api.UserDataProvider;

@Component
public class UserFilter extends BasicAuthFilter {

    private final UserDataProvider userDataProvider;

    @Autowired
    public UserFilter(UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(UserFilter.class + " initiated");
    }

    @Override
    public void destroy() {
        log.info(UserFilter.class + " destroyed");
    }

    @Override
    void doAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ( isUriPublic(request.getRequestURI()) )
            return;
        checkIfUserIsLoggedIn();
    }

    private boolean isUriPublic(String requestURI) {
        for (String uriPattern : Validation.PUBLIC_URIS) {
            if ( matcher.match(uriPattern, requestURI) )
                return true;
        }
        for (String publicSuffix : Validation.PUBLIC_FILES) {
            if ( requestURI.endsWith(publicSuffix) )
                return true;
        }
        return false;
    }

    private void checkIfUserIsLoggedIn() throws Exception {
        if ( "".equals(userDataProvider.getEmail()) ) {
            throw new Exception("User is not logged in.");
        }
    }

}
