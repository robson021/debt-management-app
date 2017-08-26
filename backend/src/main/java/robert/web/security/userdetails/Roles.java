package robert.web.security.userdetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

class Roles {

    static final List<SimpleGrantedAuthority> ROLE_USER;

    static final List<SimpleGrantedAuthority> ROLE_ADMIN;

    static {
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("ROLE_USER");
        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");

        ROLE_USER = Collections.singletonList(roleUser);
        ROLE_ADMIN = Collections.unmodifiableList(Arrays.asList(roleUser, roleAdmin));
    }

    private Roles() {
    }
}
