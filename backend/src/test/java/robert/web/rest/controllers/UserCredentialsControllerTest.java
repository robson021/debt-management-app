package robert.web.rest.controllers;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.security.userdetails.provider.UserDetailsProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UserCredentialsControllerTest extends SpringWebMvcTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDetailsProvider userDetailsProvider;

    @Test
    public void getOtherUsersDetails() throws Exception {
        for (int i = 0; i < 5; i++)
            userService.saveNewUser(TestUtils.generateNewUser());

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