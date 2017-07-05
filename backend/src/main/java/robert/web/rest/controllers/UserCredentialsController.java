package robert.web.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.UserInfoProvider;

@RestController
@RequestMapping("/credentials")
public class UserCredentialsController {

	private final UserService userService;

	private final UserInfoProvider userInfoProvider;

	public UserCredentialsController(UserService userService, UserInfoProvider userInfoProvider) {
		this.userService = userService;
		this.userInfoProvider = userInfoProvider;
	}

	@GetMapping("/other-users")
	public List<UserInfoDTO> getOtherUsersDetails() {
		long userId = userInfoProvider.getUserDetails().getUserId();
		List<User> users = userService.findOtherUsersExceptGiven(userId);
		return UserAssembler.convertToUserInfoDTOs(users);
	}

}
