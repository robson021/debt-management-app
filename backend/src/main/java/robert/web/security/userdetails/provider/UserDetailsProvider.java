package robert.web.security.userdetails.provider;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserDetailsProvider {

    long getUserId();

    String getUserEmail();

    Collection<? extends GrantedAuthority> getAuthorities();
}
