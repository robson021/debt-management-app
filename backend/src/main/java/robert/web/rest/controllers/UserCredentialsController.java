package robert.web.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.security.userdetails.provider.UserDetailsProvider;

import java.util.List;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class UserCredentialsController {

    private final UserService userService;

    private final UserDetailsProvider userDetailsProvider;

    @GetMapping("/other-users")
    public List<UserInfoDTO> getOtherUsersDetails() {
        List<User> users = userService.findOtherUsersExceptGiven(userDetailsProvider.getUserId());
        return UserAssembler.convertToUserInfoDTOs(users);
    }

}
