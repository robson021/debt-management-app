package robert.web.rest.controllers.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public void handleAll(HttpServletResponse response) throws IOException {
		response.sendRedirect("/error-page.html");
	}
}
