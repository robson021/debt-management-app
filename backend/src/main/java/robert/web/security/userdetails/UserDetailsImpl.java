package robert.web.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import robert.db.entities.User;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final long userId;

    private final String username;

    private final String password;

    private final List<? extends GrantedAuthority> authorities;

    UserDetailsImpl(User user) {
        this.userId = user.getId();
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.hasAdminRole() ? Roles.ROLE_ADMIN : Roles.ROLE_USER;
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
