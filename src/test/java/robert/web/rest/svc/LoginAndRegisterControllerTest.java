package robert.web.rest.svc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import robert.SpringWebMvcTest;
import robert.TestUtils;
import robert.db.dao.MainDao;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.rest.svc.api.LoginAndRegisterCtrl;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginAndRegisterControllerTest extends SpringWebMvcTest {

    @Autowired
    private MainDao mainDao;

    @Before
    public void init() throws Exception {
        super.initMockMvc();
    }

    @Test
    public void registerNewUser() throws Exception {
        UserInfoDTO user = new UserInfoDTO();
        user.setEmail("testuser@ttt.pl");
        user.setName("Test");
        user.setSurname("User");
        user.setPassword("Password.1234");

        mockMvc.perform(post(LoginAndRegisterCtrl.REGISTER_URL).content(TestUtils.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUser() throws Exception {
		UserInfoDTO user = UserAssembler.convertToUserInfoDTOs(Collections.singletonList(TestUtils.getTestUser()))
				.get(0);

        mainDao.saveUser(user);

        mockMvc.perform(post(LoginAndRegisterCtrl.LOGIN_URL).content(TestUtils.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}