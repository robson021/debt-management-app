package robert.web.svc;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import robert.web.security.auth.JwtAuthenticationToken;

@Component
public class UserInfoProviderImpl implements UserInfoProvider {

	@Override
	public JwtAuthenticationToken getUserDetails() {
		Authentication authentication = SecurityContextHolder //
				.getContext()
				.getAuthentication();
		try {
			return (JwtAuthenticationToken) authentication;
		} catch (Exception e) {
			return null;
		}
	}
}
