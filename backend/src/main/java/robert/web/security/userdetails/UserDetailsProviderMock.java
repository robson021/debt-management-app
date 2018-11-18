package robert.web.security.userdetails;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import robert.DevSettings;

import java.util.Collection;

@Service
@ConditionalOnMissingBean(name = "UserDetailsProvider")
public class UserDetailsProviderMock implements UserDetailsProvider {

    @Override
    public long getUserId() {
        return 1L;
    }

    @Override
    public String getUserEmail() {
        return DevSettings.USER.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Roles.ROLE_ADMIN;
    }
}
