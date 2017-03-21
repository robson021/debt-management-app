package robert.web.rest.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import robert.db.UniversalDao;
import robert.db.entities.User;
import robert.web.request.data.UserDataProvider;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

    private final UniversalDao dao;

    private final UserDataProvider userDataProvider;

    @Autowired
    public CredentialController(UniversalDao dao, UserDataProvider userDataProvider) {
        this.dao = dao;
        this.userDataProvider = userDataProvider;
    }

    @RequestMapping("/is-admin")
    public Boolean checkIfAdmin() {
        return userDataProvider.isAdmin();
    }

    @RequestMapping("/other-users")
    public List<UserInfoDTO> getOtherUsersDetails() {
        List<User> users = dao.findOtherUsersExceptGiven(userDataProvider.getUserId());
        return UserAssembler.convertToUserInfoDTOs(users);
    }

}
