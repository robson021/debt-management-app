package robert.web.security.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Roles {

    static final List<SimpleGrantedAuthority> ROLE_USER;

    static final List<SimpleGrantedAuthority> ROLE_ADMIN;

    static {
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("ROLE_USER");
        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
        SimpleGrantedAuthority roleActuator = new SimpleGrantedAuthority("ROLE_ACTUATOR");

        ROLE_USER = Collections.singletonList(roleUser);
        ROLE_ADMIN = Arrays.asList(roleUser, roleAdmin, roleActuator);
    }

    private Roles() {
    }
}
