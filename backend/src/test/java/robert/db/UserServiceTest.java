package robert.db;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.tools.SpringTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.UserInfoDTO;

import java.util.List;

public class UserServiceTest extends SpringTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @Before
    public void setup() {
        user = userService.saveNewUser(TestUtils.generateNewUser());
    }

    @Test
    public void findOtherUsersExceptGiven() {
        List<User> otherUsers = userService.findOtherUsersExceptGiven(user.getId());

        Assertions.assertThat(otherUsers)
                .doesNotContain(user);
    }

    @Test
    public void findUserByEmail() {
        User userByEmail = userService.findUserByEmail(user.getEmail());
        Assertions.assertThat(userByEmail)
                .isEqualTo(user);
    }

    @Test
    @DirtiesContext
    public void findAllUsers() {
        userService.findAllUsers();
    }

    @Test
    public void saveNewDtoUser() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setAccountNo("12342422");
        userInfoDTO.setEmail("dto.test@aa.com");
        userInfoDTO.setName("aaaaaa");
        userInfoDTO.setSurname("bbbb");
        userInfoDTO.setPassword("Passs.123");

        User user = userService.saveNewUser(userInfoDTO);

        Assertions.assertThat(user.getId())
                .isGreaterThan(0);
    }

    @Test
    public void changePassword() {
        String newPassword = "newPassword";
        userService.changePassword(user.getId(), newPassword);
        user = userService.findUserById(user.getId());

        boolean arePasswordsEqual = passwordEncoder.matches(newPassword, user.getPassword());
        Assert.assertTrue(arePasswordsEqual);
    }

    @Test
    public void changeEmail() {
        final String newEmail = "new@email.com";
        userService.changeEmail(user.getId(), newEmail);

        String email = userService.findUserById(user.getId())
                .getEmail();

        Assertions.assertThat(newEmail)
                .isEqualTo(email);
    }

}
