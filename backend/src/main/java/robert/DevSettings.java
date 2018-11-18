package robert;

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

    public static final User USER = new User();

    private final UserService userService;

    private final PaymentService paymentService;

    public DevSettings(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    static {
        USER.setEmail("test@t.pl");
        USER.setName("Example");
        USER.setSurname("User");
        USER.setPassword("Passwd.123");
        USER.setAdminRole(true);
        USER.setAccountNo(RandomStringUtils.randomNumeric(10));
    }

    @PostConstruct
    public void init() {
        userService.saveNewUser(USER);
        System.out.println("saved test USER: " + USER.toString());

        Stream.of(new User(), new User(), new User(), new User())
                .forEach(u -> {
                    u.setEmail("USER@mail." + RandomStringUtils.randomAlphabetic(3).toLowerCase());
                    u.setName(RandomStringUtils.randomAlphabetic(6));
                    u.setSurname(RandomStringUtils.randomAlphabetic(6));
                    String password = "P.1" + RandomStringUtils.randomAlphanumeric(7);
                    u.setPassword(password);
                    u.setAccountNo(RandomStringUtils.randomNumeric(10));
                    u.setAccountNo(RandomStringUtils.randomNumeric(12));
                    userService.saveNewUser(u);
                });

        long userId = userService.findUserByEmail(USER.getEmail()).getId();
        User borrower = userService.findUserById(userId + 1);

        Stream.of(new PaymentDTO(), new PaymentDTO())
                .forEach(p -> {
                    p.setAmount(ThreadLocalRandom.current().nextDouble() * 150);
                    p.setDescription("Example Payment");
                    p.setBorrowerSurname(borrower.getSurname());
                    p.setBorrowerName(borrower.getName());
                    p.setBorrowerId(borrower.getId());
                    paymentService.addDebtor(userId, p);
                });
    }
}
