package robert.web.rest.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import robert.db.entities.User;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends SpringWebMvcTest {

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

}