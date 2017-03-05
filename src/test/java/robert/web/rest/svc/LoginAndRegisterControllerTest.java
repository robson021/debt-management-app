package robert.web.rest.svc;

import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import robert.SpringWebMvcTest;
import robert.TestUtils;
import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.web.security.JwtUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LoginAndRegisterControllerTest extends SpringWebMvcTest {

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() throws Exception {
		initMockMvc();
	}

	@Test
	public void registerNewUser() throws Exception {
		User user = TestUtils.generateNewUser();
		MvcResult mvcResult = mockMvc.perform(post("/auth/register/")
				.content(TestUtils.asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void loginUser() throws Exception {
		User user = userRepository.save(TestUtils.generateNewUser());

		MvcResult mvcResult = mockMvc.perform(post("/auth/login/")
				.content(TestUtils.asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String token = mvcResult.getResponse()
				.getContentAsString();

		Assertions.assertThat(token)
				.isNotEmpty();

		System.out.println("received token:\n\t" + token);
		Claims userClaims = JwtUtils.getUserClaims("Bearer " + token);

		Assertions.assertThat(Long.parseLong(userClaims.getSubject()))
				.isEqualTo(user.getId());

		Assertions.assertThat(Boolean.valueOf(userClaims.get("role").toString()))
				.isEqualTo(user.getRole());

	}

}