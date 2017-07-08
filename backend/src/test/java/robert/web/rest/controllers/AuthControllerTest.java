package robert.web.rest.controllers;

import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.SimpleMessageDTO;
import robert.web.security.auth.JwtAuthenticationToken;
import robert.web.security.auth.JwtUtils;
import robert.web.svc.UserInfoProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends SpringWebMvcTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInfoProvider userInfoProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void registerNewUser() throws Exception {
		User user = TestUtils.generateNewUser();
		MvcResult mvcResult = mockMvc.perform(post("/auth/register/").content(TestUtils.asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		System.out.println(mvcResult.getResponse()
				.getContentAsString());
	}

	@Test
	public void loginUser() throws Exception {
		User user = TestUtils.generateNewUser();
		String originalPassword = user.getPassword();
		user.setPassword(passwordEncoder.encode(originalPassword));
		userRepository.save(user);

		user.setPassword(originalPassword);

		MvcResult mvcResult = mockMvc.perform(post("/auth/login/").content(TestUtils.asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String result = mvcResult.getResponse()
				.getContentAsString();

		String token = TestUtils.jsonToObject(result, SimpleMessageDTO.class)
				.getMessage();

		Assertions.assertThat(token)
				.isNotEmpty();

		System.out.println("received token:\n\t" + token);
		Claims userClaims = JwtUtils.getUserClaims("Bearer " + token);

		// check user's data  from decoded token
		Assertions.assertThat(Long.parseLong(userClaims.getId()))
				.isEqualTo(user.getId());

		Assertions.assertThat(userClaims.getSubject())
				.isEqualTo(user.getEmail());

	}

	@Test
	public void validateToken() throws Exception {
		JwtAuthenticationToken details = new JwtAuthenticationToken(null, null, 1L);
		Mockito.when(userInfoProvider.getUserDetails())
				.thenReturn(details);

		mockMvc.perform(get("/auth/am-i-logged-in"))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldFailTokenValidation() throws Exception {
		JwtAuthenticationToken details = new JwtAuthenticationToken(null, null, -1L);
		Mockito.when(userInfoProvider.getUserDetails())
				.thenReturn(details);

		mockMvc.perform(get("/auth/am-i-logged-in"))
				.andExpect(status().is4xxClientError());
	}

}