package robert.web.request.data;

import javax.servlet.http.HttpServletRequest;

public interface UserDataProvider {

    void setData(HttpServletRequest request);

    long getUserId();

    String getUserEmail();

    boolean isAdmin();

    HttpServletRequest getRequest();

}
