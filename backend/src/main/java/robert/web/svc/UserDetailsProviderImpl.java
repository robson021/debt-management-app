package robert.web.svc;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import robert.web.security.userdetails.UserDetailsImpl;
import robert.web.svc.api.UserDetailsProvider;

import java.util.Collection;

@Service
public class UserDetailsProviderImpl implements UserDetailsProvider {

	@Override
	public UserDetailsImpl getUserDetails() {
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
