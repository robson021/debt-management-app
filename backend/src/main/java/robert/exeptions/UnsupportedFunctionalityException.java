package robert.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Functionality is unavailable")
public class UnsupportedFunctionalityException extends RuntimeException {
}
