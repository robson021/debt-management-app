package robert.web.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.svc.api.UserService;
import robert.exeptions.UnsupportedFunctionalityException;
import robert.web.rest.dto.UserInfoDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

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
        log.info("Registered new USER: {}", userDTO);
    }

}
