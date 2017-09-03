package robert.web.rest.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import robert.exeptions.UnsupportedFunctionalityException;
import robert.svc.api.MailerService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.svc.api.UserDetailsProvider;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailsProvider userDetailsProvider;

    private final UserService userService;

    private final MailerService mailerService;

    private final String logFileName;

    public AdminController(UserDetailsProvider userDetailsProvider, UserService userService, MailerService mailerService, Environment env) {
        this.userDetailsProvider = userDetailsProvider;
        this.userService = userService;
        this.mailerService = mailerService;
        logFileName = env.getProperty("logging.file");
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

    @GetMapping("/server-logs")
    public ResponseEntity<?> downloadLogs() throws IOException {
        if ( logFileName == null ) {
            throw new UnsupportedFunctionalityException();
        }
        File file = new File(logFileName);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
