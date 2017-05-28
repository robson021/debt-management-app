package robert.web.rest.svc;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import robert.db.DatabaseService;
import robert.db.entities.User;
import robert.web.request.data.UserDataProvider;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.security.auth.SecurityUtils;

@RestController
@RequestMapping("/credentials")
@AllArgsConstructor
public class CredentialController {

	private final DatabaseService dbService;

	@GetMapping("/other-users")
	public List<UserInfoDTO> getOtherUsersDetails() {
		List<User> users = dbService.findOtherUsersExceptGiven(SecurityUtils.getUserDetails().getUserId());
		return UserAssembler.convertToUserInfoDTOs(users);
	}

}
