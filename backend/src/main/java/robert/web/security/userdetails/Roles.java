package robert.web.security.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public final class Roles {

    public static final List<SimpleGrantedAuthority> ROLE_ADMIN;

    public static final List<SimpleGrantedAuthority> ROLE_USER;

    static {
        var roleUser = new SimpleGrantedAuthority("ROLE_USER");
        var roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
        var roleActuator = new SimpleGrantedAuthority("ROLE_ACTUATOR");

        ROLE_USER = List.of(roleUser);
        ROLE_ADMIN = List.of(roleUser, roleAdmin, roleActuator);
    }

    private Roles() {
    }
}
