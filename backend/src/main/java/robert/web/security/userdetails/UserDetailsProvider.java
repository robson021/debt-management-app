package robert.web.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDetailsProvider {

    private UserDetailsImpl getUserDetails() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return (UserDetailsImpl) principal;
    }

    public long getUserId() {
        return getUserDetails().getUserId();
    }

    public String getUserEmail() {
        return getUserDetails().getUsername();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserDetails().getAuthorities();
    }
}
