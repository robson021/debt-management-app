package robert.web.security.userdetails.provider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import robert.DevSettings;
import robert.web.security.userdetails.Roles;

import java.util.Collection;

@Service
@ConditionalOnMissingBean(name = "UserDetailsProvider")
public class UserDetailsProviderMock implements UserDetailsProvider {

    @Override
    public long getUserId() {
        return DevSettings.TEST_USER_ID;
    }

    @Override
    public String getUserEmail() {
        return DevSettings.TEST_USER.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Roles.ROLE_ADMIN;
    }
}
