package robert.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You do not have admin privileges")
public class NotAnAdminException extends RuntimeException {
}
