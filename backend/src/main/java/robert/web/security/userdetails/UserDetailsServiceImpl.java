package robert.web.security.userdetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import robert.db.entities.User;
import robert.db.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Login attempt: {}", email);

        User user = userRepository.findOneByEmail(email);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        if ( log.isDebugEnabled() ) {
            log.debug("Loaded: {}", userDetails);
        }

        return userDetails;
    }
}
