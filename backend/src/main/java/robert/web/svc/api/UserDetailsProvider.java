package robert.web.svc.api;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import robert.web.security.userdetails.UserDetailsImpl;

public interface UserDetailsProvider {

    UserDetailsImpl getUserDetails();

    long getUserId();

    String getUserEmail();

    Collection<? extends GrantedAuthority> getAuthorities();
}
