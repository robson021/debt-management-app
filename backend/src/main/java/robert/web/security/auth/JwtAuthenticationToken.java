package robert.web.security.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public final class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final String email;

	private final long userId;

	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String email, long userId) {
		super(authorities);
		this.email = email;
		this.userId = userId;
	}

	@Override
	public Object getCredentials() {
		return "N/A";
	}

	@Override
	public Object getPrincipal() {
		return email;
	}

	public long getUserId() {
		return userId;
	}
}
