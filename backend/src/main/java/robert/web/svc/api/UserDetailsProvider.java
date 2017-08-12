package robert.web.svc.api;

import robert.web.security.userdetails.UserDetailsImpl;

public interface UserDetailsProvider {

	UserDetailsImpl getUserDetails();

	long getUserId();

	String getUserEmail();
}
