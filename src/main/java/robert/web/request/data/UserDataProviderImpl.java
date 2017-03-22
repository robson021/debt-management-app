package robert.web.request.data;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import robert.web.security.JwtUtils;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDataProviderImpl implements UserDataProvider {

    private long userId;

    private String userEmail;

    private boolean isAdmin;

    private HttpServletRequest request;

    @Override
    public void setData(HttpServletRequest request) {
        this.userId = JwtUtils.getUserId(request);
        this.userEmail = JwtUtils.getUserEmail(request);
        this.isAdmin = JwtUtils.isAdmin(request);
        this.request = request;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }
}
