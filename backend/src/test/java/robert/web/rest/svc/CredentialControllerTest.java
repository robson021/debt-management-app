package robert.web.rest.svc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import robert.SpringWebMvcTest;
import robert.TestUtils;
import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.web.request.data.UserDataProvider;

public class CredentialControllerTest extends SpringWebMvcTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDataProvider userDataProvider;

	@Before
	public void setup() throws Exception {
		this.initMockMvc();
	}

	@Test
	public void getOtherUsersDetails() throws Exception {

		List<User> users = Stream.of(TestUtils.generateNewUser(), TestUtils.generateNewUser(), TestUtils.generateNewUser(), //
				TestUtils.generateNewUser(), TestUtils.generateNewUser(), TestUtils.generateNewUser())
				.collect(Collectors.toList());

		userRepository.save(users);

		User user = users.get(1);

		Mockito.when(userDataProvider.getUserId())
				.thenReturn(user.getId());

		String response = mockMvc.perform(get("/credentials/other-users"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Assertions.assertThat(response)
				.doesNotContain(user.getEmail())
				.doesNotContain(user.getName())
				.doesNotContain(user.getSurname());
	}

}