package robert.dev;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import robert.db.entities.User;
import robert.db.svc.api.PaymentService;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.PaymentDTO;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

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
    public void init() {
        String testUserEmail = "test@t.pl";
        User user = new User();
        user.setEmail(testUserEmail);
        user.setName("Example");
        user.setSurname("User");
        String testUserPassword = "Passwd.123";
        user.setPassword(testUserPassword);
        user.setAdminRole(true);
        user.setAccountNo(RandomStringUtils.randomNumeric(10));

        Stream.of(new User(), new User(), new User(), new User())
                .forEach(u -> {
                    u.setEmail("user@mail." + RandomStringUtils.randomAlphabetic(3).toLowerCase());
                    u.setName(RandomStringUtils.randomAlphabetic(6));
                    u.setSurname(RandomStringUtils.randomAlphabetic(6));
                    String password = "P.1" + RandomStringUtils.randomAlphanumeric(7);
                    u.setPassword(password);
                    u.setAccountNo(RandomStringUtils.randomNumeric(10));
                    u.setAccountNo(RandomStringUtils.randomNumeric(12));
                    userService.saveNewUser(u);
                });

        userService.saveNewUser(user);
        System.out.println("saved test user: " + user.toString());
        System.out.println("test user password: " + testUserPassword);

        long userId = userService.findUserByEmail(testUserEmail).getId();
        User borrower = userService.findUserById(userId - 1);

        Stream.of(new PaymentDTO(), new PaymentDTO())
                .forEach(p -> {
                    p.setAmount(ThreadLocalRandom.current().nextDouble() * 150);
                    p.setDescription("Example Paymnet");
                    p.setBorrowerSurname(borrower.getSurname());
                    p.setBorrowerName(borrower.getName());
                    p.setBorrowerId(borrower.getId());
                    paymentService.addDebtor(userId, p);
                });
    }
}
