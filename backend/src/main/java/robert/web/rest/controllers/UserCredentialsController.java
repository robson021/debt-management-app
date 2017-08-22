package robert.web.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.api.UserDetailsProvider;

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
