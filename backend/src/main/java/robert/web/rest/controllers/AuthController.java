package robert.web.rest.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.svc.api.UserService;
import robert.exeptions.UnsupportedFunctionalityException;
import robert.web.rest.dto.UserInfoDTO;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    private final boolean isRegistrationEnabled;

    public AuthController(@Value("${robert.registrationEnabled}") String registration, UserService userService) {
        this.userService = userService;
        this.isRegistrationEnabled = Boolean.parseBoolean(registration);
        log.info("Is registration enabled? - {}", isRegistrationEnabled);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerNewUser(@RequestBody UserInfoDTO userDTO) {
        log.info("Registration attempt: {}", userDTO);
        if (!isRegistrationEnabled) {
            throw new UnsupportedFunctionalityException();
        }
        userService.saveNewUser(userDTO);
        log.info("Registered new user: {}", userDTO);
    }

}
