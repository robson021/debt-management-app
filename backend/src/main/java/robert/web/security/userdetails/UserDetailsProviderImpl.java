package robert.web.security.userdetails;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile("prod")
public class UserDetailsProviderImpl implements UserDetailsProvider {

    private UserDetailsImpl getUserDetails() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return (UserDetailsImpl) principal;
    }

    @Override
    public long getUserId() {
        return getUserDetails().getUserId();
    }

    @Override
    public String getUserEmail() {
        return getUserDetails().getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserDetails().getAuthorities();
    }
}
