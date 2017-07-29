package robert.web.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.UserInfoProvider;

import java.util.List;

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

	@GetMapping("/my-notes")
	public List<Note> getUsersNotes() {
		return userService.getAllUsersNotes(userInfoProvider.getUserDetails().getUserId());
	}

	@PostMapping("/add-note")
	@ResponseStatus(HttpStatus.OK)
	public void addNote(@RequestBody Note note) {
		userService.saveNewNote(note, userInfoProvider.getUserDetails().getUserId());
	}

}
