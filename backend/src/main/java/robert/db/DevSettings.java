package robert.db;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import robert.db.entities.User;
import robert.db.repo.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("dev")
@Component
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

		List<User> users = Stream.of(new User(), new User(), new User(), new User())
				.collect(Collectors.toList());

		users.forEach(u -> {
			u.setEmail("user@mail." + RandomStringUtils.randomAlphabetic(3));
			u.setName(RandomStringUtils.randomAlphabetic(6));
			u.setSurname(RandomStringUtils.randomAlphabetic(6));
			u.setPassword("P.1" + RandomStringUtils.randomAlphanumeric(4));
		});

		users.add(user);
		Iterable<User> saved = userRepository.save(users);
		saved.forEach(System.out::println);
	}
}
