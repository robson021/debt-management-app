package robert.db;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import robert.db.entities.User;
import robert.db.svc.DbService;
import robert.web.rest.dto.PaymentDTO;

@Profile("dev")
@Component
public class DevSettings implements CommandLineRunner {

	private final DbService databaseService;

	public DevSettings(DbService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	public void run(String... strings) throws Exception {
		String testUserEmail = "test@t.pl";
		User user = new User();
		user.setEmail(testUserEmail);
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

		users.forEach(u -> {
			u.setAccountNo(RandomStringUtils.randomNumeric(12));
			System.out.println(u);
			databaseService.saveEntity(u);
		});

		Long id = databaseService.findUserByEmail(testUserEmail)
				.getId();
		User borrower = databaseService.findUserById(id - 1);

		List<PaymentDTO> payments = Stream.of(new PaymentDTO(), new PaymentDTO())
				.collect(Collectors.toList());

		payments.forEach(p -> {
			p.setAmount(new Random().nextDouble() * 150);
			p.setDescription("Example Paymnet");
			p.setBorrowerSurname(borrower.getSurname());
			p.setBorrowerName(borrower.getName());
			p.setBorrowerId(borrower.getId());
			databaseService.addDebtor(id, p);
		});
	}
}
