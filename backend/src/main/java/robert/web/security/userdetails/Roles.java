package robert.web.security.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Roles {
	List<SimpleGrantedAuthority> ROLE_USER = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

	List<SimpleGrantedAuthority> ROLE_ADMIN = Arrays.asList(ROLE_USER.get(0), new SimpleGrantedAuthority("ROLE_ADMIN"));
}
