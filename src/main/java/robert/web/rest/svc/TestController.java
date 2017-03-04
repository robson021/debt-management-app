package robert.web.rest.svc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/hello")
	public ResponseEntity<?> hello() {
		return ResponseEntity.ok("hello world");
	}
}
