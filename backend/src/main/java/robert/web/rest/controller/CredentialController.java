package robert.web.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.User;
import robert.db.svc.DbService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.security.auth.SecurityUtils;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

	private final DbService dbService;

	public CredentialController(DbService dbService) {
		this.dbService = dbService;
	}

	@GetMapping("/other-users")
	public List<UserInfoDTO> getOtherUsersDetails() {
		List<User> users = dbService.findOtherUsersExceptGiven(SecurityUtils.getUserDetails()
				.getUserId());
		return UserAssembler.convertToUserInfoDTOs(users);
	}

}
