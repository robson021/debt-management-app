package robert.web.rest.svc.api;

import org.springframework.http.ResponseEntity;
import robert.exeptions.InvalidEmailException;
import robert.exeptions.InvalidPasswordPatternException;
import robert.exeptions.UserAuthException;
import robert.exeptions.UserNotFoundException;
import robert.web.rest.dto.UserInfoDTO;

public interface LoginAndRegisterCtrl {

    String REST_SERVICE_PREFIX = "/user";

    String REGISTER_URL = REST_SERVICE_PREFIX + "/register/";

    String LOGIN_URL = REST_SERVICE_PREFIX + "/login/";

    ResponseEntity<?> registerNewUser(UserInfoDTO userDTO) throws InvalidEmailException, InvalidPasswordPatternException;

    ResponseEntity<?> loginUser(UserInfoDTO userDTO) throws UserNotFoundException, UserAuthException;

}
