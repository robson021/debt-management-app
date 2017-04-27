package robert.web.rest.svc;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import robert.db.DatabaseService;
import robert.db.entities.User;
import robert.web.request.data.UserDataProvider;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;

@RestController
@RequestMapping("/credentials")
@AllArgsConstructor
public class CredentialController {

    private final DatabaseService dbService;

    private final UserDataProvider userDataProvider;


    @RequestMapping("/is-admin")
    public Boolean checkIfAdmin() {
        return userDataProvider.isAdmin();
    }

    @RequestMapping("/other-users")
    public List<UserInfoDTO> getOtherUsersDetails() {
        List<User> users = dbService.findOtherUsersExceptGiven(userDataProvider.getUserId());
        return UserAssembler.convertToUserInfoDTOs(users);
    }

}
