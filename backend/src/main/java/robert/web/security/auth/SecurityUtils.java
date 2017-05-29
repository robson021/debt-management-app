package robert.web.security.auth;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static JwtAuthenticationToken getUserDetails() {
		return (JwtAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
	}

}
