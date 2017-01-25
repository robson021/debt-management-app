package robert.web.rest.svc.api;

import org.springframework.http.ResponseEntity;

import robert.exeptions.InvalidEmailException;
import robert.exeptions.InvalidPasswordException;
import robert.exeptions.UserNotFoundException;
import robert.web.rest.dto.UserInfoDTO;

public interface LoginAndRegisterCtrl {

    String REGISTER_URL = "/register/";

    String LOGIN_URL = "/login/";

    ResponseEntity<?> registerNewUser(UserInfoDTO userDTO) throws InvalidEmailException, InvalidPasswordException;

    ResponseEntity<?> loginUser(UserInfoDTO userDTO) throws UserNotFoundException;

}
