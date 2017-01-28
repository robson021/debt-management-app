package robert.web.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import robert.web.session.api.UserDataProvider;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDataProviderImpl implements UserDataProvider {

	private long id = -1;

    private String email;

    private boolean adminPrivileges = false;

    private CsrfToken csrfToken = null;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setIdAndEmail(long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public void enableAdminPrivileges() {
        this.adminPrivileges = true;
    }

    @Override
    public boolean isAdmin() {
        return adminPrivileges;
    }

    @Override
    public void setNewCsrfToken(CsrfToken csrfToken) {
        this.csrfToken = csrfToken;
    }

    @Override
    public CsrfToken getCsrfToken() {
        return csrfToken;
    }

    @Override
    public void invalidateData() {
        this.id = -1;
        this.email = "";
        this.adminPrivileges = false;
    }
}
