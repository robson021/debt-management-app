package robert.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import robert.db.entities.User;
import robert.db.repo.UserRepository;

@Component
@Profile("dev")
public class DevSettings {

    private final UserRepository userRepository;

    @Autowired
    public DevSettings(UserRepository userRepository) {
        this.userRepository = userRepository;
        init();
    }

    private void init() {
        User user = new User();
        user.setEmail("test@t.pl");
        user.setName("Example");
        user.setSurname("User");
        user.setPassword("Passwd.123");

        userRepository.save(user);
        System.out.println("Added example user: " + user.getEmail());
    }
}
