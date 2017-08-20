package robert.web.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.svc.api.MailerService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.api.UserDetailsProvider;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private final UserDetailsProvider userDetailsProvider;

	private final UserService userService;

	private final MailerService mailerService;

	public AdminController(UserDetailsProvider userDetailsProvider, UserService userService, MailerService mailerService) {
		this.userDetailsProvider = userDetailsProvider;
		this.userService = userService;
		this.mailerService = mailerService;
	}

	@GetMapping("/all-users")
	public List<UserInfoDTO> getAllUsers() {
		List<User> allUsers = userService.findAllUsers();
		return UserAssembler.convertToUserInfoDTOs(allUsers);
	}

	@PutMapping("/change-password")
	@ResponseStatus(HttpStatus.OK)
	public void changeUserPassword(@RequestBody UserInfoDTO user) {
		userService.changePassword(user.getId(), user.getPassword());
	}

	@PutMapping("/change-email")
	@ResponseStatus(HttpStatus.OK)
	public void changeUserEmail(@RequestBody UserInfoDTO user) {
		userService.changeEmail(user.getId(), user.getEmail());
	}

	@GetMapping("/all-notes")
	public List<Note> getAllNotes() {
		return userService.getAllNotes();
	}

	@PostMapping("/send-server-logs")
	@ResponseStatus(HttpStatus.OK)
	public void sendServerLogs() {
		mailerService.sendServerLogs(userDetailsProvider.getUserEmail());
	}

}
