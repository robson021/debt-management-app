package robert.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Functionality is unavailable")
public class UnsupportedFunctionalityException extends RuntimeException {

}
