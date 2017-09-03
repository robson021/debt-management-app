package robert.web.rest.controllers.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Profile("prod")
public class GlobalRestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception ex) {
        log.error("Error: {}", ex.getMessage());
        return ResponseEntity //
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error");
    }
}