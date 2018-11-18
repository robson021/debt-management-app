package robert;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.PaymentDTO;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Profile("dev")
@Component
public class DevSettings {

    public static final User TEST_USER = new User();

    private final UserService userService;

    public DevSettings(UserService userService) {
        this.userService = userService;
    }

    static {
        TEST_USER.setEmail("test@t.pl");
        TEST_USER.setName("Example");
        TEST_USER.setSurname("User");
        TEST_USER.setPassword("Passwd.123");
        TEST_USER.setAdminRole(true);
        TEST_USER.setAccountNo(RandomStringUtils.randomNumeric(10));
    }

    @PostConstruct
    public void init() {
        userService.saveNewUser(TEST_USER);
        System.out.println("saved test TEST_USER: " + TEST_USER.toString());

        Stream.of(new User(), new User(), new User(), new User())
                .forEach(u -> {
                    u.setEmail("TEST_USER@mail." + RandomStringUtils.randomAlphabetic(6).toLowerCase());
                    u.setName(RandomStringUtils.randomAlphabetic(6));
                    u.setSurname(RandomStringUtils.randomAlphabetic(6));
                    String password = "P.1" + RandomStringUtils.randomAlphanumeric(7);
                    u.setPassword(password);
                    u.setAccountNo(RandomStringUtils.randomNumeric(10));
                    u.setAccountNo(RandomStringUtils.randomNumeric(12));
                    userService.saveNewUser(u);
                });

        long userId = userService.findUserByEmail(TEST_USER.getEmail()).getId();
        User borrower = userService.findUserById(userId + 1);

        Stream.of(new PaymentDTO(), new PaymentDTO())
                .forEach(p -> {
                    p.setAmount(ThreadLocalRandom.current().nextDouble() * 150);
                    p.setDescription("Example Payment");
                    p.setBorrowerSurname(borrower.getSurname());
                    p.setBorrowerName(borrower.getName());
                    p.setBorrowerId(borrower.getId());
                    // TODO: fix on mysql profile
                    //paymentService.addDebtor(userId, p);
                });
    }
}
