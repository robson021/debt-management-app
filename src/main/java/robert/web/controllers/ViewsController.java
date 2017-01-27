package robert.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewsController {

	@RequestMapping("/")
	public String getHomePage() {
		return "index";
	}

	@RequestMapping("/my-debts")
	public String getMyDebts() {
		return "my-debts";
	}
}
