package robert.web.request.data;

import io.jsonwebtoken.Claims;

public interface UserDataProvider {

    void setData(Claims claims);

    long getUserId();

    String getUserEmail();

    boolean isAdmin();

}
