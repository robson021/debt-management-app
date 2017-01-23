package robert.web.rest.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import robert.db.dao.MainDao;
import robert.db.entities.User;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.svc.api.LoginAndRegisterCtrl;
import robert.web.session.api.UserDataProvider;

@RestController
public class LoginAndRegisterController implements LoginAndRegisterCtrl {

    private final MainDao mainDao;

    private final UserDataProvider userDataProvider;

    @Autowired
    public LoginAndRegisterController(MainDao mainDao, UserDataProvider userDataProvider) {
        this.mainDao = mainDao;
        this.userDataProvider = userDataProvider;
    }

    @Override
    @RequestMapping(value = REGISTER_URL, method = RequestMethod.POST)
    public ResponseEntity<?> registerNewUser(@RequestBody UserInfoDTO userDTO) throws Exception {
        User user = mainDao.saveUser(userDTO);
        loginUser(user);
        return new ResponseEntity<>("User has been registered and logged in.", HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody UserInfoDTO userDTO) throws Exception {
        User user = mainDao.findUser(userDTO);
        loginUser(user);
        return new ResponseEntity<>("User has been logged in.", HttpStatus.OK);
    }

    private void loginUser(User user) {
        userDataProvider.setIdAndEmail(user.getId(), user.getEmail());
    }
}
