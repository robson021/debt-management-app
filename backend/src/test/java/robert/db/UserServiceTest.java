package robert.db;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.tools.SpringTest;
import robert.tools.TestUtils;

public class UserServiceTest extends SpringTest {

	@Autowired
	private UserService userService;

	@Test
	public void findOtherUsersExceptGiven() {
		User user = userService.saveNewUser(TestUtils.generateNewUser());
		List<User> otherUsers = userService.findOtherUsersExceptGiven(user.getId());

		Assertions.assertThat(otherUsers)
				.doesNotContain(user);
	}
}
