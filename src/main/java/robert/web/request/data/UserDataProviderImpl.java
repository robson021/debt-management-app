package robert.web.request.data;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import io.jsonwebtoken.Claims;
import robert.web.security.JwtUtils;

@Component
@RequestScope
public class UserDataProviderImpl implements UserDataProvider {

    private long userId;

    private String userEmail;

    private boolean isAdmin;

    public void setData(Claims claims) {
        this.userId = JwtUtils.getUserId(claims);
        this.userEmail = JwtUtils.getUserEmail(claims);
        this.isAdmin = JwtUtils.isAdmin(claims);
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

}
