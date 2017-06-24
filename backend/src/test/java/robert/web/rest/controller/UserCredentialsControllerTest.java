package robert.web.rest.controller;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.svc.UserInfoProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UserCredentialsControllerTest extends SpringWebMvcTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfoProvider userInfoProvider;

	@Test
	public void getOtherUsersDetails() throws Exception {
		for (int i = 0; i < 5; i++) {
			userService.saveNewUser(TestUtils.generateNewUser());
		}

		User user = userService.saveNewUser(TestUtils.generateNewUser());

		Mockito.when(userInfoProvider.getUserDetails())
				.thenReturn(TestUtils.mockAuthWithNoRole(user));

		String response = mockMvc.perform(get("/credentials/other-users/"))
				.andReturn()
				.getResponse()
				.getContentAsString();

		Assertions.assertThat(response)
				.doesNotContain(user.getEmail())
				.doesNotContain(user.getName())
				.doesNotContain(user.getSurname());

	}

}