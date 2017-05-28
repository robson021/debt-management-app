package robert.web.request.data;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDataProvider extends UserDetails {

	long getUserId();

}
