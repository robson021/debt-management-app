package robert.web.exception.handlers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import robert.exeptions.InvalidEmailException;
import robert.exeptions.InvalidPasswordPatternException;
import robert.exeptions.UserAuthException;
import robert.exeptions.UserNotFoundException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	private static final Logger log = Logger.getLogger(ExceptionHandlerControllerAdvice.class);

	@ExceptionHandler({InvalidEmailException.class, InvalidPasswordPatternException.class})
	public ResponseEntity<String> invalidUserCredentials(Exception exception) {
		log.error(exception.getMessage());
		return new ResponseEntity<>("Invalid email or password pattern", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFound(Exception exception) {
		log.error(exception.getMessage());
		return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserAuthException.class)
	public ResponseEntity<String> invalidPassword(Exception exception) {
		log.error(exception.getMessage());
		return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
	}

}
