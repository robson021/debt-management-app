package robert.web.rest.svc;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import robert.exeptions.InvalidEmailException;
import robert.exeptions.InvalidPasswordException;
import robert.exeptions.UserNotFoundException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = Logger.getLogger(ExceptionHandlerControllerAdvice.class);

    protected static final String INVALID_CREDENTIALS = "Invalid email or password pattern";

    protected static final String USER_NOT_FOUND = "User not found.";

    @ExceptionHandler({ InvalidEmailException.class, InvalidPasswordException.class })
    public ResponseEntity<String> invalidUserCredentials(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFound(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

}
