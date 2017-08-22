package robert.web.security.userdetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import robert.db.entities.User;

public class UserDetailsImpl implements UserDetails {

	private final long userId;

	private final String username;

	private final String password;

	private final List<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(User user) {
		Assert.notNull(user, "No user has been found");
		this.userId = user.getId();
		this.username = user.getEmail();
		this.password = user.getPassword();
		if ( user.hasAdminRole() ) {
			this.authorities = Roles.ROLE_ADMIN;
		} else {
			this.authorities = Roles.ROLE_USER;
		}
	}

	public long getUserId() {
		return userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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

	@Override
	public String toString() {
		return "UserDetailsImpl{" + "userId=" + userId + ", username='" + username + '\'' + ", authorities=" + authorities + '}';
	}

}
