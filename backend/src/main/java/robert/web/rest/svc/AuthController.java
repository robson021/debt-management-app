package robert.web.rest.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import robert.db.DatabaseService;
import robert.db.entities.User;
import robert.exeptions.AuthException;
import robert.web.request.data.UserDataProvider;
import robert.web.rest.dto.SimpleMessageDTO;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.security.auth.JwtUtils;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final UserDataProvider userDataProvider;

	private final DatabaseService dbService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public void registerNewUser(@RequestBody UserInfoDTO userDTO) throws Exception {
		dbService.saveNewUser(userDTO);
		log.info("Registered new user:", userDTO);
	}

	@PostMapping("/login")
	public SimpleMessageDTO loginUser(@RequestBody UserInfoDTO userDTO) throws Exception {
		String token = tryToLogUserIn(userDTO);
		log.debug("\nlogged in user: {}\nwith token: {}", userDTO, token);
		return new SimpleMessageDTO(token);
	}

	@GetMapping("/am-i-logged-in")
	public HttpStatus validateToken() {
		if (userDataProvider.getUserId() > 0) {
			return HttpStatus.OK;
		}
		throw new AuthException("Token is not valid");
	}

	private String tryToLogUserIn(UserInfoDTO user) {
		User u = dbService.findUserByEmail(user.getEmail());
		if (!u.getPassword().equals(user.getPassword()))
			throw new AuthException("Passwords do not match");

		return JwtUtils.generateToken(u);
	}
}
