package robert.web.rest.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.User;
import robert.db.svc.DatabaseService;
import robert.exeptions.AuthException;
import robert.web.rest.dto.SimpleMessageDTO;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.security.auth.JwtUtils;
import robert.web.svc.UserInfoProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final DatabaseService databaseService;

	private final UserInfoProvider userInfoProvider;

	public AuthController(DatabaseService databaseService, UserInfoProvider userInfoProvider) {
		this.databaseService = databaseService;
		this.userInfoProvider = userInfoProvider;
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public void registerNewUser(@RequestBody UserInfoDTO userDTO) throws Exception {
		databaseService.saveNewUser(userDTO);
		log.info("Registered new user:", userDTO);
	}

	@GetMapping("/am-i-logged-in")
	public HttpStatus validateToken() {
		if ( userInfoProvider.getUserDetails().getUserId() > 0 ) {
			return HttpStatus.OK;
		}
		throw new AuthException("Token is not valid");
	}

	@PostMapping("/login")
	public SimpleMessageDTO loginUser(@RequestBody UserInfoDTO userDTO) throws Exception {
		String token = tryToLogUserIn(userDTO);
		if (log.isDebugEnabled()) {
			log.debug("Logged user in: {}\nwith token: {}", userDTO, token);
		}
		return new SimpleMessageDTO(token);
	}

	private String tryToLogUserIn(UserInfoDTO user) {
		User u = databaseService.findUserByEmail(user.getEmail());
		if ( !u.getPassword().equals(user.getPassword()) ) {
			throw new AuthException("Passwords do not match");
		}
		return JwtUtils.generateToken(u);
	}

	@ExceptionHandler(AuthException.class)
	public void exceptionHandler(AuthException ex, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
	}
}
