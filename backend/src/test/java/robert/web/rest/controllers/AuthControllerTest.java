package robert.web.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import robert.db.entities.User;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;

public class AuthControllerTest extends SpringWebMvcTest {

    @Test
    public void registerNewUser() throws Exception {
        User user = TestUtils.generateNewUser();
        mockMvc.perform(post("/auth/register/").content(TestUtils.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}