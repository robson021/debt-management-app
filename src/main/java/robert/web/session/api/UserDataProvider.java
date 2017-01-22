package robert.web.session.api;

import org.springframework.security.web.csrf.CsrfToken;

public interface UserDataProvider {

    long getId();

    String getEmail();

    void setIdAndEmail(long id, String email);

    void enableAdminPrivileges();

    boolean isAdmin();

    void setNewCsrfToken(CsrfToken csrfToken);

    CsrfToken getCsrfToken();

    void invalidateData();

}
