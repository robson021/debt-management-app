package robert.web.rest.svc;

import org.springframework.http.HttpEntity;

import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.svc.api.LoginAndRegisterCtrl;

public class LoginAndRegisterController implements LoginAndRegisterCtrl {

    @Override
    public HttpEntity<?> registerNewUser(UserInfoDTO userDTO) {
        return null;
    }

    @Override
    public HttpEntity<?> loginUser(UserInfoDTO userDTO) {
        return null;
    }
}
