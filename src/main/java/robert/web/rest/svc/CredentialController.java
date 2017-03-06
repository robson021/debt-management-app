package robert.web.rest.svc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import robert.db.UniversalDao;
import robert.db.entities.User;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.security.JwtUtils;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

    private final UniversalDao dao;

    @Autowired
    public CredentialController(UniversalDao dao) {
        this.dao = dao;
    }

    @RequestMapping("/is-admin")
    public Boolean checkIfAdmin(HttpServletRequest request) {
        return JwtUtils.isAdmin(request);
    }

    @RequestMapping("/other-users")
    public List<UserInfoDTO> getOtherUsersDetails(HttpServletRequest request) {
        Long userId = JwtUtils.getUserId(request);
        List<User> users = dao.findOtherUsersExceptGiven(userId);

        return UserAssembler.convertToUserInfoDTOs(users);
    }

}
