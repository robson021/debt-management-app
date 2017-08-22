package robert.web.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.svc.api.UserDetailsProvider;

public class UserCredentialsControllerTest extends SpringWebMvcTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsProvider userDetailsProvider;

	@Test
	public void getOtherUsersDetails() throws Exception {
		for (int i = 0; i < 5; i++) {
			userService.saveNewUser(TestUtils.generateNewUser());
		}

		User user = userService.saveNewUser(TestUtils.generateNewUser());

		Mockito.when(userDetailsProvider.getUserId())
				.thenReturn(user.getId());

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