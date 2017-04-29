package robert.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import robert.db.entities.User;
import robert.db.repo.UserRepository;

@Component
@Profile("dev")
@AllArgsConstructor
public class DevSettings implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... strings) throws Exception {
        User user = new User();
        user.setEmail("test@t.pl");
        user.setName("Example");
        user.setSurname("User");
        user.setPassword("Passwd.123");

        userRepository.save(user);
    }
}
