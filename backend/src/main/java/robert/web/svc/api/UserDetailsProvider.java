package robert.web.svc.api;

import org.springframework.security.core.GrantedAuthority;
import robert.web.security.userdetails.UserDetailsImpl;

import java.util.Collection;

public interface UserDetailsProvider {

	UserDetailsImpl getUserDetails();

	long getUserId();

	String getUserEmail();

	Collection<? extends GrantedAuthority> getAuthorities();
}
