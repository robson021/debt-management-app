package robert.web.rest.svc.api;

import org.springframework.http.ResponseEntity;

import robert.web.rest.dto.UserInfoDTO;

public interface LoginAndRegisterCtrl {

    String REGISTER_URL = "/register/";

    String LOGIN_URL = "/login/";

    ResponseEntity<?> registerNewUser(UserInfoDTO userDTO) throws Exception;

    ResponseEntity<?> loginUser(UserInfoDTO userDTO) throws Exception;

}
