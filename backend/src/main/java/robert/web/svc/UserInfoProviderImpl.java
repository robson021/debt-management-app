package robert.web.svc;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import robert.web.security.auth.JwtAuthenticationToken;

@Component
public class UserInfoProviderImpl implements UserInfoProvider {

	@Override
	public JwtAuthenticationToken getUserDetails() {
		return (JwtAuthenticationToken) SecurityContextHolder //
				.getContext()
				.getAuthentication();
	}
}
