package robert.web.svc;

import robert.web.security.auth.JwtAuthenticationToken;

public interface UserInfoProvider {
	JwtAuthenticationToken getUserDetails();

	long getUserId();
}
