package robert.db;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import robert.db.entities.User;
import robert.db.svc.api.PaymentService;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.PaymentDTO;

@Profile("dev")
@Component
public class DevSettings {

	private final UserService userService;

	private final PaymentService paymentService;

	public DevSettings(UserService userService, PaymentService paymentService) {
		this.userService = userService;
		this.paymentService = paymentService;
	}

	@PostConstruct
	public void init() throws Exception {
		String testUserEmail = "test@t.pl";
		User user = new User();
		user.setEmail(testUserEmail);
		user.setName("Example");
		user.setSurname("User");
		user.setPassword("Passwd.123");
		user.setRole(true);
		user.setAccountNo(RandomStringUtils.randomNumeric(10));

		List<User> users = Arrays.asList(new User(), new User(), new User(), new User());

		users.forEach(u -> {
			u.setEmail("user@mail." + RandomStringUtils.randomAlphabetic(3).toLowerCase());
			u.setName(RandomStringUtils.randomAlphabetic(6));
			u.setSurname(RandomStringUtils.randomAlphabetic(6));
			String password = "P.1" + RandomStringUtils.randomAlphanumeric(7);
			u.setPassword(password);
			u.setAccountNo(RandomStringUtils.randomNumeric(10));
		});

		users.forEach(u -> {
			u.setAccountNo(RandomStringUtils.randomNumeric(12));
			userService.saveNewUser(u);
		});

		userService.saveNewUser(user);
		System.out.println("saved test user: " + user.toString());

		Long userId = userService.findUserByEmail(testUserEmail).getId();
		User borrower = userService.findUserById(userId - 1);

		List<PaymentDTO> payments = Arrays.asList(new PaymentDTO(), new PaymentDTO());

		payments.forEach(p -> {
			p.setAmount(new Random().nextDouble() * 150);
			p.setDescription("Example Paymnet");
			p.setBorrowerSurname(borrower.getSurname());
			p.setBorrowerName(borrower.getName());
			p.setBorrowerId(borrower.getId());
			paymentService.addDebtor(userId, p);
		});
	}
}
