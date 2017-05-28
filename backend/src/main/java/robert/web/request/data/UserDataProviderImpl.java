package robert.web.request.data;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import robert.web.security.auth.JwtUtils;

public class UserDataProviderImpl implements UserDataProvider {

	private final long userId;

	private final String userEmail;

	private final Collection<GrantedAuthority> authorities;

	public UserDataProviderImpl(Claims claims) {
		this.userId = JwtUtils.getUserId(claims);
		this.userEmail = JwtUtils.getUserEmail(claims);
		if ( JwtUtils.isAdmin(claims) ) {
			authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities = Collections.emptySet();
		}
	}

	@Override
	public long getUserId() {
		return userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return userEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
