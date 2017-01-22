package robert.web.rest.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import robert.db.dao.UserDao;
import robert.db.entities.User;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.svc.api.LoginAndRegisterCtrl;
import robert.web.session.api.UserDataProvider;

@RestController
public class LoginAndRegisterController implements LoginAndRegisterCtrl {

    private final UserDao userDao;

    private final UserDataProvider userDataProvider;

    @Autowired
    public LoginAndRegisterController(UserDao userDao, UserDataProvider userDataProvider) {
        this.userDao = userDao;
        this.userDataProvider = userDataProvider;
    }

    @Override
    @RequestMapping(value = REGISTER_URL, method = RequestMethod.POST)
    public ResponseEntity<?> registerNewUser(@RequestBody UserInfoDTO userDTO) throws Exception {
        User user = userDao.saveUser(userDTO);
        userDataProvider.setIdAndEmail(user.getId(), user.getEmail());
        return new ResponseEntity<>("User has been registered.", HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody UserInfoDTO userDTO) throws Exception {
        User user = userDao.findUser(userDTO);
        userDataProvider.setIdAndEmail(user.getId(), user.getEmail());
        return new ResponseEntity<>("User has been logged in.", HttpStatus.OK);
    }
}
