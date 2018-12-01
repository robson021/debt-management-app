package robert.web.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.NoteService;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final NoteService noteService;

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
        return noteService.getAllNotes();
    }

}
