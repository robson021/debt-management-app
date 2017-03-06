package robert.web.rest.svc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.exeptions.UserAuthException;
import robert.web.rest.dto.SimpleMessageDTO;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.security.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger log = Logger.getLogger(AuthController.class);

	private final UserRepository userRepository;

	@Autowired
	public AuthController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerNewUser(@RequestBody UserInfoDTO userDTO) throws Exception {
		userRepository.save(UserAssembler.convertDtoToUser(userDTO));
		log.info("Registered new user: " + userDTO.getEmail());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public SimpleMessageDTO loginUser(@RequestBody UserInfoDTO userDTO) throws Exception {
        String token = tryToLogUserIn(userDTO);
		log.info(userDTO.getEmail() + " logged in.");
        return new SimpleMessageDTO(token);
    }

	private String tryToLogUserIn(UserInfoDTO user) throws UserAuthException {
		User u = userRepository.findOneByEmail(user.getEmail());
		if (!u.getPassword().equals(user.getPassword()))
			throw new UserAuthException();

		return JwtUtils.generateToken(u);
	}
}
