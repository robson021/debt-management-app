package robert.web.rest.svc.api;

import org.springframework.http.HttpEntity;

import robert.web.rest.dto.UserInfoDTO;

public interface LoginAndRegisterCtrl {

    HttpEntity<?> registerNewUser(UserInfoDTO userDTO);

    HttpEntity<?> loginUser(UserInfoDTO userDTO);

}
