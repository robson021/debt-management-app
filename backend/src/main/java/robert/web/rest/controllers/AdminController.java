package robert.web.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.svc.api.MailerService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.api.UserDetailsProvider;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

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
