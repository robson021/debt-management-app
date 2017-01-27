package robert.web.rest.svc;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import robert.SpringWebMvcTest;
import robert.TestUtils;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.svc.api.LoginAndRegisterCtrl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ExceptionHandlerControllerAdviceTest extends SpringWebMvcTest {

    @Before
    public void init() throws Exception {
        super.initMockMvc();
    }

    @Test
    public void userNotFound() throws Exception {
        MvcResult result = mockMvc.perform(post(LoginAndRegisterCtrl.LOGIN_URL).content(TestUtils.asJsonString(new UserInfoDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertThat(result.getResponse()
                .getContentAsString())
				.isNotNull();

    }

    @Test
    public void invalidUserCredentials() throws Exception {
        UserInfoDTO user = new UserInfoDTO();
        user.setEmail("some@user.com");
        user.setPassword("invalid");

        MvcResult result = mockMvc.perform(post(LoginAndRegisterCtrl.REGISTER_URL).content(TestUtils.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertThat(result.getResponse()
                .getContentAsString())
				.isNotNull();
	}

}