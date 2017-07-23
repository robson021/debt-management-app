package robert.web.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;

@RestController
@RequestMapping("admin")
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserInfoDTO> getAllUsers() {
		List<User> allUsers = userService.findAllUsers();
		return UserAssembler.convertToUserInfoDTOs(allUsers);
	}

	@PutMapping("change-password")
	@ResponseStatus(HttpStatus.OK)
	public void changeUserPassword(UserInfoDTO user) {
		userService.changePassword(user.getId(), user.getPassword());
	}

}
