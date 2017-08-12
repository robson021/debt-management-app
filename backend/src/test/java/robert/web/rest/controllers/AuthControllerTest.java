package robert.web.rest.controllers;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.util.FieldUtils;
import robert.db.entities.User;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.security.userdetails.UserDetailsImpl;
import robert.web.svc.api.UserDetailsProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends SpringWebMvcTest {

	@Autowired
	private UserDetailsProvider userDetailsProvider;

	private User user;

	@Before
	public void setup() {
		user = TestUtils.generateNewUser();
	}

	@Test
	public void registerNewUser() throws Exception {
		mockMvc.perform(post("/auth/register/").content(TestUtils.asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void isAdmin() throws Exception {
		FieldUtils.setProtectedFieldValue("id", user, 1L);
		user.setAdminRole(true);

		Mockito.when(userDetailsProvider.getUserDetails())
				.thenReturn(new UserDetailsImpl(user));

		Mockito.when(userDetailsProvider.getAuthorities())
				.thenCallRealMethod();

		Assertions.assertThat(checkIfAdminRequest())
				.contains("OK");

		user.setAdminRole(false);
		Mockito.when(userDetailsProvider.getUserDetails())
				.thenReturn(new UserDetailsImpl(user));

		Assertions.assertThat(checkIfAdminRequest())
				.contains("UNAUTHORIZED");
	}

	private String checkIfAdminRequest() throws Exception {
		return mockMvc.perform(get("/auth/am-i-admin/"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
	}

}