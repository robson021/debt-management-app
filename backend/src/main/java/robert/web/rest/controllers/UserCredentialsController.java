package robert.web.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.api.UserDetailsProvider;

import java.util.List;

@RestController
@RequestMapping("/credentials")
public class UserCredentialsController {

	private final UserService userService;

	private final UserDetailsProvider userDetailsProvider;

	public UserCredentialsController(UserService userService, UserDetailsProvider userDetailsProvider) {
		this.userService = userService;
		this.userDetailsProvider = userDetailsProvider;
	}

	@GetMapping("/other-users")
	public List<UserInfoDTO> getOtherUsersDetails() {
		List<User> users = userService.findOtherUsersExceptGiven(userDetailsProvider.getUserId());
		return UserAssembler.convertToUserInfoDTOs(users);
	}

	@GetMapping("/my-notes")
	public List<Note> getUsersNotes() {
		return userService.getAllUsersNotes(userDetailsProvider.getUserId());
	}

	@PostMapping("/add-note")
	@ResponseStatus(HttpStatus.OK)
	public void addNote(@RequestBody String note) {
		userService.saveNewNote(new Note(note), userDetailsProvider.getUserId());
	}

	@DeleteMapping("/remove-note/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void removeNote(@PathVariable long id) {
		userService.deleteNote(userDetailsProvider.getUserId(), id);
	}

}
