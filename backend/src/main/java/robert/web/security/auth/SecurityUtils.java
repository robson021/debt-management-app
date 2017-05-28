package robert.web.security.auth;

import org.springframework.security.core.context.SecurityContextHolder;

import robert.web.request.data.UserDataProviderImpl;

public class SecurityUtils {

	public static UserDataProviderImpl getUserDetails() {
		return (UserDataProviderImpl) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
	}

}
