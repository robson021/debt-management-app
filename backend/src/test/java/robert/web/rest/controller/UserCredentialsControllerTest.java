package robert.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import robert.db.entities.User;
import robert.db.svc.DatabaseService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.security.auth.JwtAuthenticationToken;
import robert.web.svc.UserInfoProvider;

public class UserCredentialsControllerTest extends SpringWebMvcTest {

	@Autowired
	private DatabaseService databaseService;

	@Autowired
	private UserInfoProvider userInfoProvider;

	@Test
	public void getOtherUsersDetails() throws Exception {
		for (int i = 0; i < 5; i++) {
			databaseService.saveEntity(TestUtils.generateNewUser());
		}

		User user = (User) databaseService.saveEntity(TestUtils.generateNewUser());

		Mockito.when(userInfoProvider.getUserDetails())
				.thenReturn(new JwtAuthenticationToken(Collections.emptySet(), user.getEmail(), user.getId()));

		String response = mockMvc.perform(get("/credentials/other-users/"))
				.andReturn()
				.getResponse()
				.getContentAsString();

		Assertions.assertThat(response)
				.doesNotContain(user.getEmail());

	}

}