package robert.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not allowed parameter")
public class BadParameterException extends RuntimeException {
    public BadParameterException(String message) {
        super(message);
    }
}
