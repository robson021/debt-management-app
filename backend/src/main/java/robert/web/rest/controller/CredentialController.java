package robert.web.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.User;
import robert.db.svc.DatabaseService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.UserInfoProvider;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

	private final DatabaseService databaseService;

	private final UserInfoProvider userInfoProvider;

	public CredentialController(DatabaseService databaseService, UserInfoProvider userInfoProvider) {
		this.databaseService = databaseService;
		this.userInfoProvider = userInfoProvider;
	}

	@GetMapping("/other-users")
	public List<UserInfoDTO> getOtherUsersDetails() {
		List<User> users = databaseService.findOtherUsersExceptGiven(userInfoProvider.getUserDetails()
				.getUserId());
		return UserAssembler.convertToUserInfoDTOs(users);
	}

}
