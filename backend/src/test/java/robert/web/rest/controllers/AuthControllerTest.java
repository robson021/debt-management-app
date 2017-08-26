package robert.web.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.util.FieldUtils;
import org.springframework.test.web.servlet.ResultActions;

import robert.db.entities.User;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.security.userdetails.UserDetailsImpl;
import robert.web.svc.api.UserDetailsProvider;

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

        checkIfUserHasAdminRole().andExpect(status().isOk());

        user.setAdminRole(false);
        Mockito.when(userDetailsProvider.getUserDetails())
                .thenReturn(new UserDetailsImpl(user));

        checkIfUserHasAdminRole().andExpect(status().isUnauthorized());
    }

    private ResultActions checkIfUserHasAdminRole() throws Exception {
        return mockMvc.perform(get("/auth/am-i-admin/"));
    }

}