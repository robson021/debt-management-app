package robert.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Authentication failed")
public class AuthException extends RuntimeException {

	public AuthException(String msg) {
		super(msg);
	}
}
