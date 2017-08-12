package robert.web.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.svc.api.UserDetailsProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final UserService userService;

	private final UserDetailsProvider userDetailsProvider;

	private final boolean isRegistrationEnabled;

	public AuthController(@Value("${robert.registrationEnabled}") String registration, UserDetailsProvider userDetailsProvider, UserService userService) {
		this.userDetailsProvider = userDetailsProvider;
		this.userService = userService;
		this.isRegistrationEnabled = Boolean.parseBoolean(registration);
		log.info("Is registration enabled? - {}", isRegistrationEnabled);
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public void registerNewUser(@RequestBody UserInfoDTO userDTO) throws Exception {
		if (!isRegistrationEnabled) {
			throw new RuntimeException("Registration is currently disabled");
		}
		userService.saveNewUser(userDTO);
		log.info("Registered new user:", userDTO);
	}

	@GetMapping("/am-i-admin")
	public HttpStatus checkIfAdmin() {
		boolean isAdmin = userDetailsProvider.getAuthorities()
				.stream()
				.anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

		return isAdmin ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
	}

}
